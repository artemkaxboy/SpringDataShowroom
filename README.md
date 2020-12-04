# Spring Data Showroom

[![Default branch verification](https://github.com/artemkaxboy/SpringDataShowroom/workflows/Default%20branch%20verification/badge.svg)](https://github.com/artemkaxboy/SpringDataShowroom/actions?query=workflow%3A%22Default+branch+verification%22)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/519f12293e4844d5b500d51375868bcb)](https://app.codacy.com/gh/artemkaxboy/SpringDataShowroom?utm_source=github.com&utm_medium=referral&utm_content=artemkaxboy/SpringDataShowroom&utm_campaign=Badge_Grade)

The application contains examples of uncommon use cases of Spring Data and some common practice examples like github actions examples, container building examples, etc.

## How to run app

### Using docker-compose (recommended)

Go to {PROJECT_ROOT}/docker directory, adjust properties in .env-file if needed, run docker-compose:

```bash
docker-compose up
```

Docker creates database container and runs the last published version of the app from github docker registry. API documentation and demonstration are accessible on http://localhost:8080/ by default.

### Using gradle

Make sure you have run your own database service and correctly set connection properties in src/main/resources/application.yml spring.datasource. Run the app using gradle:

```bash
./gradlew bootRun
```

API documentation and demonstration are accessible on http://localhost:8080/ by default.
