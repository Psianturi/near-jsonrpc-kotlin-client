# Security Policy

## Supported Versions

We take security seriously. The following versions are currently supported with security updates:

| Version | Supported          |
| ------- | ------------------ |
| 1.0.x   | :white_check_mark: |
| < 1.0   | :x:                |

## Reporting a Vulnerability

If you discover a security vulnerability in this project, please report it responsibly:

### Contact Information

- **Email**: posmajaniuss@gmail.com
- **GitHub Security Advisories**: [Report here](https://github.com/Psianturi/near-jsonrpc-kotlin-client/security/advisories/new)

### Reporting Process

1. **Do not** create public GitHub issues for security vulnerabilities
2. Email the security team with details of the vulnerability
3. Include:
   - Description of the vulnerability
   - Steps to reproduce
   - Potential impact
   - Suggested fix (if any)

### Response Timeline

- **Initial Response**: Within 24 hours
- **Vulnerability Assessment**: Within 72 hours
- **Fix Development**: Within 1-2 weeks for critical issues
- **Public Disclosure**: After fix is deployed and tested

## Security Best Practices

When using this library:

1. **Keep Dependencies Updated**: Regularly update to the latest versions
2. **Use HTTPS**: Always connect to NEAR RPC endpoints over HTTPS
3. **Validate Inputs**: Validate all inputs before sending to RPC calls
4. **Handle Errors Securely**: Don't expose sensitive information in error messages
5. **Rate Limiting**: Implement appropriate rate limiting for your application

## Known Security Considerations

- This library makes HTTP requests to external NEAR RPC endpoints
- API keys or sensitive data should never be hardcoded
- Always validate SSL certificates in production
- Consider implementing request signing for additional security

## Security Updates

Security updates will be released as patch versions (1.0.x) and will be documented in:
- [Security Advisories](https://github.com/Psianturi/near-jsonrpc-kotlin-client/security/advisories)
- [Release Notes](https://github.com/Psianturi/near-jsonrpc-kotlin-client/releases)
- [Changelog](https://github.com/Psianturi/near-jsonrpc-kotlin-client/blob/main/CHANGELOG.md)

Thank you for helping keep this project secure! 🛡️