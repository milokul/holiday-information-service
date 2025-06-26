# Holiday Information Service

This service provides information about public holidays for different countries. It allows comparing holidays between
two countries starting from a specific date.

## Features

- Compare holidays between two countries
- RESTful API with Swagger UI documentation
- Date validation and formatting
- Reactive programming support with WebFlux
- It uses external API service: https://openholidaysapi.org

## Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

## Building the Application

To build and run the application, run:
```run.sh```

## Usage
To use this application, try for example: http://127.0.0.1:8080/compare-holidays?countryCode1=ES&countryCode2=PL&date=2025-01-01

## Logs
Logs are in logs/ directory
