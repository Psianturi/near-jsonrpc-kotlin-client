# Contributing to NEAR JSON-RPC Kotlin Client

Thank you for your interest in contributing to the NEAR JSON-RPC Kotlin Client! This document provides guidelines and information for contributors.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Workflow](#development-workflow)
- [Project Structure](#project-structure)
- [Coding Standards](#coding-standards)
- [Testing](#testing)
- [Submitting Changes](#submitting-changes)
- [Reporting Issues](#reporting-issues)

## Code of Conduct

This project follows a code of conduct to ensure a welcoming environment for all contributors. Please read [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md) before participating.

## Getting Started

### Prerequisites

- **JDK 17 or higher**
- **Kotlin 1.9.20+** (via Gradle wrapper - auto-configured)
- **Gradle 8.3** (via Gradle wrapper - no manual install needed)
- **Node.js 22+** (only for code generation)
- **IntelliJ IDEA** or **Android Studio** (recommended)

### Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Psianturi/near-jsonrpc-kotlin-client.git
   cd near-jsonrpc-kotlin-client
   ```

2. **Build the project:**
   ```bash
   ./gradlew build
   ```

3. **Run tests:**
   ```bash
   ./gradlew test
   ```

4. **Generate code** (if making changes to the generator):
   ```bash
   cd generator
   npm install
   npm run generate
   ```

## Development Workflow

### 1. Choose an Issue

- Check [GitHub Issues](https://github.com/Psianturi/near-jsonrpc-kotlin-client/issues) for open tasks
- Look for issues labeled `good first issue` or `help wanted`
- Comment on the issue to indicate you're working on it

### 2. Create a Branch

```bash
git checkout -b feature/your-feature-name
# or
git checkout -b fix/issue-number-description
```

### 3. Make Changes

- Write clear, focused commits
- Follow the coding standards below
- Add tests for new functionality
- Update documentation as needed

### 4. Test Your Changes

```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew :packages:client:jvmTest --tests "*TestClassName*"

# Generate coverage reports (Kover)
./gradlew koverXmlReport

# Verify coverage thresholds
./gradlew koverVerify
```

### 5. Update Documentation

- Update README.md if adding new features
- Add JSDoc/KDoc comments for public APIs
- Update examples if needed

## Project Structure

```
├── generator/              # TypeScript code generator
│   ├── src/
│   │   └── index.ts       # Main generator logic
│   └── package.json
├── packages/
│   ├── client/            # Main client library
│   │   ├── src/
│   │   │   ├── commonMain/# Shared code (KMP)
│   │   │   └── jvmTest/   # JVM-specific tests
│   │   └── build.gradle.kts
│   └── types/             # Generated type definitions
│       └── src/commonMain/kotlin/com/near/jsonrpc/types/
├── gradle/                # Gradle wrapper and config
├── .github/               # GitHub Actions and templates
└── docs/                  # Documentation
```

## Coding Standards

### Kotlin Style

- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use 4 spaces for indentation
- Maximum line length: 120 characters
- Use `val` for immutable variables, `var` only when necessary

### Naming Conventions

- **Classes**: PascalCase (e.g., `JsonRpcTransport`)
- **Functions/Methods**: camelCase (e.g., `callMethod()`)
- **Variables**: camelCase (e.g., `rpcUrl`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `DEFAULT_TIMEOUT`)

### Documentation

- Add KDoc comments for all public APIs
- Include parameter descriptions and return value information
- Use `@param`, `@return`, and `@throws` tags

```kotlin
/**
 * Calls a JSON-RPC method on the NEAR network.
 *
 * @param method The RPC method name (snake_case)
 * @param params Optional parameters for the method
 * @return The response from the RPC call
 * @throws NearRpcException if the RPC call fails
 */
suspend inline fun <reified P, reified R> call(
    method: String,
    params: P? = null
): R
```

### Error Handling

- Use custom exceptions for specific error conditions
- Provide meaningful error messages
- Don't expose sensitive information in error messages
- Use Kotlin's `Result` type for operations that may fail

## Testing

### Unit Tests

- Test individual components in isolation
- Use mocking for external dependencies
- Cover both success and error scenarios
- Aim for high code coverage (>80%)

```kotlin
@Test
fun `should handle successful RPC call`() = runTest {
    // Arrange
    val mockEngine = MockEngine { respond(successResponse) }
    val transport = JsonRpcTransport(HttpClient(mockEngine), testUrl)

    // Act
    val result = transport.call<String, RpcResponse>("test_method", "param")

    // Assert
    assertNotNull(result)
    assertEquals("expected", result.data)
}
```

### Integration Tests

- Test end-to-end functionality
- Use real NEAR testnet endpoints
- Include both positive and negative test cases
- Don't run in CI by default (use `@Tag("integration")`)

```kotlin
@Tag("integration")
@Test
fun `should fetch real network status`() = runBlocking {
    val client = NearRpcClient(JsonRpcTransport(HttpClient(CIO), testnetUrl))
    val status = client.status()

    assertNotNull(status.chainId)
    assertTrue(status.syncInfo.latestBlockHeight > 0)
}
```

### Test Organization

- Place unit tests alongside source code in `src/test/kotlin`
- Use descriptive test method names
- Group related tests in nested classes
- Use `@DisplayName` for better test reporting

## Submitting Changes

### Commit Guidelines

Follow conventional commit format:

```
type(scope): description

[optional body]

[optional footer]
```

**Types:**
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes
- `refactor`: Code refactoring
- `test`: Test additions/changes
- `chore`: Maintenance tasks

**Examples:**
```
feat: add support for batch RPC calls
fix: handle network timeouts properly
docs: update installation instructions
test: add integration tests for validator queries
```

### Pull Request Process

1. **Create a Pull Request**
   - Use a descriptive title
   - Reference related issues with `#issue-number`
   - Provide a clear description of changes

2. **Pull Request Template**
   ```markdown
   ## Description
   Brief description of changes

   ## Type of Change
   - [ ] Bug fix
   - [ ] New feature
   - [ ] Breaking change
   - [ ] Documentation update

   ## Testing
   - [ ] Unit tests added/updated
   - [ ] Integration tests added/updated
   - [ ] Manual testing performed

   ## Checklist
   - [ ] Code follows project style guidelines
   - [ ] Documentation updated
   - [ ] Tests pass locally
   - [ ] No breaking changes
   ```

3. **Review Process**
   - Address review comments
   - Ensure CI checks pass
   - Squash commits if requested
   - Merge when approved

## Reporting Issues

### Bug Reports

When reporting bugs, please include:

- **Clear title** describing the issue
- **Steps to reproduce** the problem
- **Expected behavior** vs actual behavior
- **Environment information**:
  - OS and version
  - JDK version
  - Kotlin version
  - Library version
- **Stack traces** or error messages
- **Sample code** that demonstrates the issue

### Feature Requests

For feature requests, please include:

- **Clear description** of the proposed feature
- **Use case** and why it's needed
- **Proposed implementation** if you have ideas
- **Alternatives considered**

### Security Issues

For security-related issues, please see [SECURITY.md](SECURITY.md) for responsible disclosure guidelines.

## Recognition

Contributors will be recognized in:
- [CHANGELOG.md](CHANGELOG.md) for releases
- GitHub repository contributors list
- Project documentation

Thank you for contributing to the NEAR JSON-RPC Kotlin Client! 🎉