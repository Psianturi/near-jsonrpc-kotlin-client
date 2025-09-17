# Changelog

All notable changes to the NEAR JSON-RPC Kotlin Client will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2024-01-XX

### Added
- **Type-Safe API**: Complete type safety with strongly typed methods and responses
- **JsonRpcTransport Layer**: Robust transport layer handling all JSON-RPC protocol details
- **Auto-Generated Code**: Automatic code generation from NEAR's OpenAPI specification
- **Multiplatform Support**: Kotlin Multiplatform with JVM target (JS support planned)
- **Comprehensive Testing**: Unit tests and integration tests with real NEAR testnet
- **Snake_case Method Names**: Automatic conversion to NEAR protocol requirements
- **Error Handling**: Proper exception handling with NearRpcException
- **Documentation**: Complete README with examples and API reference

### Features
- Network status queries (`status()`)
- Block information retrieval (`block()`)
- Account queries (`query()`)
- Transaction operations (`sendTx()`, `tx()`)
- Gas price information (`gasPrice()`)
- Validator information (`validators()`)
- And many more NEAR RPC methods

### Technical Details
- **Kotlin Version**: 1.9.0
- **Ktor Version**: 2.3.4
- **Kotlinx.serialization**: 1.6.3
- **Generated Types**: 100+ data classes from NEAR OpenAPI spec
- **Test Coverage**: Unit tests with MockEngine, integration tests with real endpoints

### Changed
- Initial release - no breaking changes from previous versions

### Deprecated
- None

### Removed
- None

### Fixed
- None (initial release)

### Security
- HTTPS-only connections to RPC endpoints
- No sensitive data logging
- Proper error message sanitization

## Development

### Infrastructure
- Gradle build system with Kotlin DSL
- TypeScript code generator for automated code generation
- GitHub Actions CI/CD (planned)
- Comprehensive test suite

### Documentation
- Detailed README with quick start guide
- API reference documentation
- Code examples for common use cases
- Contributing guidelines
- Security policy
- Code of conduct

---

## Types of Changes

- `Added` for new features
- `Changed` for changes in existing functionality
- `Deprecated` for soon-to-be removed features
- `Removed` for now removed features
- `Fixed` for any bug fixes
- `Security` in case of vulnerabilities

## Versioning

This project uses [Semantic Versioning](https://semver.org/):

- **MAJOR** version for incompatible API changes
- **MINOR** version for backwards-compatible functionality additions
- **PATCH** version for backwards-compatible bug fixes

---

## Contributing to Changelog

When contributing to this project:

1. **For Features**: Add entries under appropriate `Added` section
2. **For Bug Fixes**: Add entries under `Fixed` section
3. **For Breaking Changes**: Add entries under `Changed` section with `BREAKING CHANGE:` footer
4. **Keep it Concise**: Use clear, descriptive language
5. **Reference Issues**: Include issue/PR numbers when applicable

Example:
```
### Fixed
- Handle network timeouts gracefully ([#123](https://github.com/Psianturi/near-jsonrpc-kotlin-client/pull/123))
```

---

For more information about upcoming releases, see the [Roadmap](https://github.com/Psianturi/near-jsonrpc-kotlin-client#roadmap) in the README.