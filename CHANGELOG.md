# Changelog

All notable changes to the NEAR JSON-RPC Kotlin Client will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.1.3](https://github.com/Psianturi/near-jsonrpc-kotlin-client/compare/v1.1.2...v1.1.3) (2025-10-06)


### Bug Fixes

* disable jvm publication task to prevent conflicts with kotlinMultiplatform ([e50d378](https://github.com/Psianturi/near-jsonrpc-kotlin-client/commit/e50d378b28d5e3833082eb90b2f3f9e0eebe1096))

## [1.1.2](https://github.com/Psianturi/near-jsonrpc-kotlin-client/compare/v1.1.1...v1.1.2) (2025-10-06)


### Bug Fixes

* remove invalid workflows permission from regen workflow ([a1b4e02](https://github.com/Psianturi/near-jsonrpc-kotlin-client/commit/a1b4e02ccc3d2e7b895f80a179e1ea3bcd861ee7))
* resolve publication conflicts by removing duplicate jvm publication ([fae0750](https://github.com/Psianturi/near-jsonrpc-kotlin-client/commit/fae0750c3299e8f59cda2449382cc661152e3b05))

## [1.1.1](https://github.com/Psianturi/near-jsonrpc-kotlin-client/compare/v1.1.0...v1.1.1) (2025-10-06)


### Bug Fixes

* make Maven Central publishing optional to prevent CI failures ([5de7386](https://github.com/Psianturi/near-jsonrpc-kotlin-client/commit/5de738606fadec21704fe4d0ce3796ceaa8ca69b))
* strip v prefix from release tag for proper package versioning ([1bed642](https://github.com/Psianturi/near-jsonrpc-kotlin-client/commit/1bed6421efeeaf7ca91e0ff6034e577516a37ecc))

## [1.1.0](https://github.com/Psianturi/near-jsonrpc-kotlin-client/compare/v1.0.1...v1.1.0) (2025-10-06)


### Features

* improve test coverage, expand typed endpoints, add Maven Central config ([1f34ef4](https://github.com/Psianturi/near-jsonrpc-kotlin-client/commit/1f34ef419ba11e929dad0988f9745a223ba182ac))

## [1.0.1](https://github.com/Psianturi/near-jsonrpc-kotlin-client/compare/v1.0.0...v1.0.1) (2025-10-06)


### Bug Fixes

* **ci:** remove unused Node.js setup causing lock file error ([9dec61b](https://github.com/Psianturi/near-jsonrpc-kotlin-client/commit/9dec61be76638a41841548cb2d98f6064f8ca419))

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
