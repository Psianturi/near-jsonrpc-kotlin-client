# NEAR JSON-RPC Kotlin Client

A Kotlin Multiplatform client for the NEAR JSON-RPC API. This project is auto-generated from the official [nearcore `openapi.json` spec](https://github.com/near/nearcore/blob/master/chain/jsonrpc/openapi/openapi.json).

## Project Status

This project is currently under active development.

**Completed Milestones:**
- ✅ **Project Setup:** Configured a multi-module Gradle project that is correctly recognized by Android Studio/IntelliJ IDEA.
- ✅ **Generator Foundation:** Built a robust Node.js/TypeScript generator that reads the official `openapi.json` spec.
- ✅ **Type Generation (`types` module):** The generator successfully creates serializable Kotlin `data class` models from the OpenAPI schemas. The `packages/types` module is verified as buildable.
- ✅ **Client Scaffolding (`client` module):** The generator now creates a `NearRpcClient.kt` file with a Ktor `HttpClient`. It generates basic, parameter-less RPC methods. The `packages/client` module is also verified as buildable.
- ✅ **Housekeeping:** Added a comprehensive `.gitignore` file and updated this `README.md` with project status and instructions.

**Next Steps:**
- 🚧 **Enhance Client Generator:** Add logic to handle RPC methods with request parameters.
- 🚧 **Implement Error Handling:** Improve the generated client to properly handle JSON-RPC errors.
- 🚧 **Add Unit & Integration Tests:** Create test suites for both the `types` and `client` modules.
- 🚧 **Setup CI/CD:** Add GitHub Actions to automate code generation, building, and testing.

## Project Structure

This repository uses a monorepo structure managed by Gradle.

- `build.gradle.kts`: The root Gradle build file, making the project recognizable by IDEs like Android Studio.
- `settings.gradle.kts`: The root Gradle settings file, which includes the sub-projects.
- **`/generator`**: A Node.js/TypeScript project responsible for reading the `openapi.json` spec and generating all Kotlin code for the `types` and `client` packages.
- **`/packages`**: Contains the generated Kotlin Multiplatform library modules.
  - **`/packages/types`**: Contains the generated Kotlin `data class` models. This is the data layer of the library.
  - **`/packages/client`**: Will contain the generated RPC client functions that use the models from the `types` package.

## How to Work with This Project

### Prerequisites
- JDK (Java Development Kit) 17 or higher
- Node.js v18 or higher
- Android Studio or IntelliJ IDEA (Recommended for Kotlin development)

### 1. Generating Code

The Kotlin source code is not meant to be edited manually. It is generated from the OpenAPI spec. To re-generate the code after making changes to the generator:

1.  Navigate to the `generator` directory:
    ```bash
    cd generator
    ```
2.  Install dependencies:
    ```bash
    npm install
    ```
3.  Run the generator:
    ```bash
    npm run generate
    ```
This will delete and re-create the contents of `packages/types/src` and `packages/client/src`.

### 2. Building and Testing (in Android Studio / IntelliJ IDEA)

1.  Open the **root folder** (`near-jsonrpc-kotlin-client`) of this project in Android Studio or IntelliJ IDEA.
2.  Allow the IDE to complete the **Gradle Sync** process. This will download all necessary dependencies.
3.  To build the entire project, you can use the Gradle panel or run the following command in the IDE's terminal:

    - **On Windows (PowerShell):**
      ```powershell
      cmd /c ".\gradlew.bat build"
      ```
    - **On Linux/macOS:**
      ```bash
      ./gradlew build
      ```

## License

This project is licensed under the Apache License, Version 2.0. See the [LICENSE](LICENSE) file for details.