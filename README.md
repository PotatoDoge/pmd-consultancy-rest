# PMD Consultancy REST API

A REST API service for project intake requests, built with **Hexagonal Architecture (Ports and Adapters)**.

## 🚀 Quick Start

```bash
# Build
./gradlew clean build

# Run
./gradlew bootRun
```

**API**: http://localhost:8081/api/v1
**Swagger UI**: http://localhost:8081/api/v1/swagger-ui.html

### Quick Test
```bash
curl -X POST http://localhost:8081/api/v1/project-intakes \
  -H "Content-Type: application/json" \
  -d '{
    "clientName": "Acme Corp",
    "industry": "Technology",
    "companySize": "50-200",
    "context": "Digital transformation",
    "painPoints": ["Legacy systems"],
    "budgetRange": {"min": 50000, "max": 150000, "currency": "USD"}
  }'
```

## 📚 Documentation

### Start Here (Read in Order)

1. **[INSTRUCTIONS.md](INSTRUCTIONS.md)** 📖
   Complete hexagonal architecture implementation guide
   → Read this to learn the pattern (or let your AI agent read it to replicate this architecture)

2. **[ABOUT.md](ABOUT.md)** 📋
   This project's architecture, structure, and business logic
   → Read this to understand how this specific project works

3. **[.claudecontext](.claudecontext)** 🤖
   Quick reference for AI assistants
   → Quick overview of the project

## 🏗️ Architecture

```
Infrastructure (REST, Kafka, DTOs)
    ↓
Application (Ports, Services)
    ↓
Domain (Business Logic - Zero Dependencies)
```

**Key Principle**: Dependencies flow inward only

## 📂 Structure

```
src/main/kotlin/com/pmdconsultancyrest/
├── domain/              # Pure business logic (no frameworks)
│   ├── model/          # ProjectIntake, BudgetRange
│   ├── service/        # ProjectIntakeDomainService
│   └── exception/      # Business exceptions
├── application/         # Orchestration
│   ├── port/in/        # ProcessProjectIntakePort
│   ├── port/out/       # ProjectIntakeEventPublisher
│   └── service/        # ProcessProjectIntakeService
└── infrastructure/      # Framework code
    ├── adapter/in/rest/     # REST controller, DTOs, mappers
    └── adapter/out/kafka/   # Kafka publisher, DTOs, mappers
```

## 🛠️ Tech Stack

- Kotlin + Spring Boot 4.0.2
- Gradle 9.3.0 + Java 21+
- Spring Kafka + SpringDoc OpenAPI

## ✅ Architecture Compliance

100% compliant with hexagonal architecture:
- ✅ Domain layer: Zero framework dependencies
- ✅ Rich domain models with business logic
- ✅ Ports define clear contracts
- ✅ Thin adapters (only DTO ↔ Domain mapping)

---

**For detailed information**, see [INSTRUCTIONS.md](INSTRUCTIONS.md) and [ABOUT.md](ABOUT.md)
