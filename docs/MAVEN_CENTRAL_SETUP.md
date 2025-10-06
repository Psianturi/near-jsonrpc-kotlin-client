# Maven Central Publishing Setup

This document describes how to publish the NEAR JSON-RPC Kotlin Client to Maven Central.

## Prerequisites

1. **Sonatype OSSRH Account**
   - Create account at https://issues.sonatype.org/
   - Create JIRA ticket requesting new project namespace
   - Wait for approval (usually 1-2 business days)

2. **GPG Key for Signing**
   - Generate GPG key: `gpg --gen-key`
   - Export private key: `gpg --export-secret-keys -o secring.gpg`
   - Get key ID: `gpg --list-secret-keys --keyid-format=long`
   - Publish public key: `gpg --keyserver keyserver.ubuntu.com --send-keys YOUR_KEY_ID`

## Configuration

### Local Development

Create `~/.gradle/gradle.properties`:

```properties
ossrhUsername=your-sonatype-username
ossrhPassword=your-sonatype-password

signing.keyId=YOUR_KEY_ID (last 8 characters)
signing.password=your-gpg-passphrase
signing.secretKeyRingFile=/path/to/secring.gpg

# Alternative: use in-memory key (recommended for CI)
signing.key=-----BEGIN PGP PRIVATE KEY BLOCK-----\n...\n-----END PGP PRIVATE KEY BLOCK-----
```

### CI/CD (GitHub Actions)

Add these secrets to your GitHub repository:

```
OSSRH_USERNAME=your-sonatype-username
OSSRH_PASSWORD=your-sonatype-password
SIGNING_KEY_ID=YOUR_KEY_ID
SIGNING_PASSWORD=your-gpg-passphrase
SIGNING_KEY=<your-ascii-armored-gpg-key>
```

To get ASCII-armored key:
```bash
gpg --armor --export-secret-keys YOUR_KEY_ID
```

## Publishing Process

### 1. Local Testing

Test local publishing to Maven Local:

```bash
./gradlew publishToMavenLocal
```

Verify artifacts in `~/.m2/repository/io/near/`

### 2. Snapshot Publishing

Publish snapshot version (ends with `-SNAPSHOT`):

```bash
./gradlew publish -Pversion=1.0.0-SNAPSHOT
```

Snapshots are available immediately at:
```
https://s01.oss.sonatype.org/content/repositories/snapshots/
```

### 3. Release Publishing

1. **Update version** (remove `-SNAPSHOT`):
   ```bash
   ./gradlew publish -Pversion=1.0.0
   ```

2. **Close and Release on Sonatype**:
   - Login to https://s01.oss.sonatype.org/
   - Go to "Staging Repositories"
   - Find your repository (io.near-xxxx)
   - Click "Close" and wait for validation
   - Click "Release" to publish to Maven Central

3. **Wait for sync** (2-4 hours):
   - Check https://repo1.maven.org/maven2/io/near/

## Automated Release Workflow

The project includes automated release workflow:

```yaml
# .github/workflows/publish-maven-central.yml
on:
  release:
    types: [published]
```

This workflow automatically:
1. Builds artifacts
2. Runs tests
3. Signs artifacts
4. Publishes to Maven Central staging
5. Auto-closes and releases (if configured)

## Consumption

Once published, users can add dependencies:

### Gradle (Kotlin DSL)

```kotlin
dependencies {
    implementation("io.near:near-jsonrpc-client:1.0.0")
    // Types module (optional, included transitively)
    implementation("io.near:near-jsonrpc-types:1.0.0")
}
```

### Gradle (Groovy)

```groovy
dependencies {
    implementation 'io.near:near-jsonrpc-client:1.0.0'
}
```

### Maven

```xml
<dependency>
    <groupId>io.near</groupId>
    <artifactId>near-jsonrpc-client</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Troubleshooting

### "401 Unauthorized" Error

- Verify OSSRH credentials are correct
- Check token has proper permissions
- Ensure namespace is approved

### Signing Failures

- Verify GPG key is not expired
- Check passphrase is correct
- Ensure public key is published to keyserver

### Validation Failures on Close

Common issues:
- Missing POM information (name, description, URL)
- Missing licenses section
- Missing developers section
- Missing SCM information
- Artifacts not signed

All these are configured in `build.gradle.kts`.

## Verification

After publishing, verify:

1. **Maven Central Search**: https://search.maven.org/
2. **Direct Repository**: https://repo1.maven.org/maven2/io/near/
3. **POM File**: Check metadata is complete
4. **Signatures**: Verify .asc files are present

## Resources

- Sonatype OSSRH Guide: https://central.sonatype.org/publish/publish-guide/
- GPG Guide: https://central.sonatype.org/publish/requirements/gpg/
- Gradle Signing Plugin: https://docs.gradle.org/current/userguide/signing_plugin.html