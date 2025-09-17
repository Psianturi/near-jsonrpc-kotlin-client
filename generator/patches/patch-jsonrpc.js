#!/usr/bin/env node
// generator/patches/patch-jsonrpc.js
// Node >=14. Run: node generator/patches/patch-jsonrpc.js
const fs = require('fs');
const path = require('path');

const CLIENT_SRC = path.resolve(__dirname, '../../packages/client/src'); // adjust if your layout differs
const KOTLIN_EXT = '.kt';

function walk(dir) {
  const res = [];
  for (const name of fs.readdirSync(dir)) {
    const p = path.join(dir, name);
    const st = fs.statSync(p);
    if (st.isDirectory()) res.push(...walk(p));
    else if (p.endsWith(KOTLIN_EXT)) res.push(p);
  }
  return res;
}

function camelToSnake(name) {
  return name.replace(/([a-z0-9])([A-Z])/g, '$1_$2').toLowerCase();
}

function findFunctions(content) {
  // crude regex to find 'suspend fun name(params): ReturnType {'
  const regex = /suspend\s+fun\s+([A-Za-z0-9_]+)\s*\(([^)]*)\)\s*:\s*([A-Za-z0-9_<>,?.\s]+)\s*\{/g;
  const matches = [];
  let m;
  while ((m = regex.exec(content)) !== null) {
    matches.push({
      name: m[1],
      paramsRaw: m[2].trim(),
      returnType: m[3].trim(),
      index: m.index,
      headerLen: m[0].length
    });
  }
  return matches;
}

function findMatchingBrace(content, startPos) {
  let depth = 0;
  for (let i = startPos; i < content.length; i++) {
    if (content[i] === '{') depth++;
    else if (content[i] === '}') {
      depth--;
      if (depth === 0) return i;
    }
  }
  return -1;
}

function patchFile(filePath) {
  let content = fs.readFileSync(filePath, 'utf8');
  const funcs = findFunctions(content);
  if (!funcs.length) return false;

  let changed = false;
  // add rpcTransport property in class if missing (simple heuristic)
  if (content.includes("class") && !content.includes("rpcTransport")) {
    // try insert after class header constructor block ' {'
    const classMatch = content.match(/class\s+[A-Za-z0-9_]+\s*\([^)]*\)\s*{/);
    if (classMatch) {
      const insertPos = content.indexOf('{', classMatch.index) + 1;
      const inject = `\n    // JSON-RPC transport helper injected by patch script\n    private val rpcTransport = com.near.jsonrpc.JsonRpcTransport(this.client, this.baseUrl)\n`;
      content = content.slice(0, insertPos) + inject + content.slice(insertPos);
    }
  }

  // process functions from last to first to avoid index shift
  for (let i = funcs.length - 1; i >= 0; i--) {
    const f = funcs[i];
    const bodyStart = content.indexOf('{', f.index + f.headerLen - 1);
    const bodyEnd = findMatchingBrace(content, bodyStart);
    if (bodyStart < 0 || bodyEnd < 0) continue;

    // parse params
    const params = f.paramsRaw === '' ? [] : f.paramsRaw.split(',').map(p => p.trim()).filter(Boolean);
    if (params.length > 1) {
      // skip complex functions
      continue;
    }

    // determine param name & type
    let paramsTypeSnippet = 'Nothing?';
    let paramsExpr = 'null';
    if (params.length === 1) {
      // param like 'argName: Type'
      const parts = params[0].split(':').map(s => s.trim());
      if (parts.length >= 2) {
        const paramName = parts[0];
        const paramType = parts.slice(1).join(':');
        paramsTypeSnippet = paramType;
        paramsExpr = paramName;
      } else {
        // fallback: treat as Any?
        paramsTypeSnippet = 'Any?';
        paramsExpr = params[0].split(':')[0] || params[0];
      }
    }

    const methodSnake = camelToSnake(f.name);
    const newBody = `{
        // patched to use JsonRpcTransport helper (method: "${methodSnake}")
        return rpcTransport.call<${paramsTypeSnippet}, ${f.returnType}>("${methodSnake}", ${paramsExpr})
    }`;

    // replace the whole body
    content = content.slice(0, bodyStart) + newBody + content.slice(bodyEnd + 1);
    changed = true;
  }

  if (changed) {
    fs.writeFileSync(filePath + '.bak', fs.readFileSync(filePath));
    fs.writeFileSync(filePath, content, 'utf8');
    console.log('Patched:', filePath);
    return true;
  }
  return false;
}

console.log('Scanning', CLIENT_SRC);
const allFiles = walk(CLIENT_SRC);
const modified = [];
for (const f of allFiles) {
  try {
    const ok = patchFile(f);
    if (ok) modified.push(f);
  } catch (e) {
    console.error('Error patching', f, e);
  }
}

console.log('Done. Modified files count:', modified.length);
if (modified.length > 0) {
  console.log('Backups created with .bak extension. Please review changes and commit if OK.');
} else {
  console.log('No files patched (maybe all functions are complex / multiple params). Consider editing generator templates directly.');
}