# PMD Consultancy REST API - Project Documentation

## Overview
This project implements a REST API for submitting project intake requests following **Hexagonal Architecture (Ports and Adapters)** pattern.

## Architecture

### Hexagonal Architecture (Ports and Adapters)
This project strictly follows hexagonal architecture principles to ensure:
- **Testability**: Domain logic can be tested without any framework
- **Maintainability**: Clear separation of concerns
- **Flexibility**: Easy to swap technologies without affecting business logic
- **Technology Independence**: Domain has zero framework dependencies

## Project Structure

```
src/main/kotlin/com/pmdconsultancyrest/
├── domain/                            # Pure business logic (NO framework dependencies)
│   ├── model/                        # Rich domain entities with business behavior
│   │   ├── ProjectIntake.kt         # Main domain model with validation & business rules
│   │   └── BudgetRange.kt           # Value object with validation
│   ├── service/                      # Domain services for multi-entity logic
│   │   └── ProjectIntakeDomainService.kt
│   └── exception/                    # Business rule exceptions
│       └── InvalidProjectIntakeException.kt
│
├── application/                       # Use case orchestration
│   ├── port/
│   │   ├── in/                       # Input ports (what app CAN DO)
│   │   │   └── ProcessProjectIntakePort.kt
│   │   └── out/                      # Output ports (what app NEEDS)
│   │       └── ProjectIntakeEventPublisher.kt
│   ├── service/                      # Application services (orchestration)
│   │   └── ProcessProjectIntakeService.kt
│   └── config/                       # Wire domain services as Spring beans
│       └── DomainWiringConfig.kt
│
└── infrastructure/                    # Framework-specific code
    ├── adapter/
    │   ├── in/                       # Driving adapters (receive input)
    │   │   └── rest/                 # REST adapter
    │   │       ├── dto/              # Request DTOs
    │   │       │   ├── ProjectIntakeRequest.kt
    │   │       │   └── BudgetRangeRequest.kt
    │   │       ├── mapper/           # DTO → Domain mappers
    │   │       │   └── ProjectIntakeRequestMapper.kt
    │   │       └── ProjectIntakeRestController.kt
    │   └── out/                      # Driven adapters (call external systems)
    │       └── kafka/                # Kafka adapter
    │           ├── dto/              # Event DTOs
    │           │   ├── ProjectIntakeEvent.kt
    │           │   └── BudgetRangeEvent.kt
    │           ├── mapper/           # Domain → DTO mappers
    │           │   └── ProjectIntakeEventMapper.kt
    │           └── KafkaProjectIntakePublisher.kt
    └── config/                       # Infrastructure configurations
        ├── CorsConfig.kt
        ├── JacksonConfig.kt
        └── OpenApiConfig.kt
```

## Layer Responsibilities

### Domain Layer
**Location**: `domain/`

**Purpose**: Contains all business logic and business rules

**Rules**:
- ❌ NO Spring annotations
- ❌ NO framework imports
- ✅ Pure Kotlin/Java only
- ✅ Immutable objects (val properties)
- ✅ Self-validating models
- ✅ Rich business behavior

**What's inside**:
- **Models**: Rich domain entities with validation and business logic
  - `ProjectIntake`: Main entity with business rules (priority, category, validation)
  - `BudgetRange`: Value object with its own validation
- **Services**: Domain services for multi-entity business logic
  - `ProjectIntakeDomainService`: Priority scoring, team assignment, compliance checks
- **Exceptions**: Business rule violations
  - `InvalidProjectIntakeException`

**Example Business Logic** (in ProjectIntake.kt):
```kotlin
fun isHighPriority(): Boolean
fun getCategory(): String
fun requiresSeniorReview(): Boolean
fun getEstimatedResponseTime(): Int
```

### Application Layer
**Location**: `application/`

**Purpose**: Orchestrates use cases and defines contracts (ports)

**Rules**:
- ✅ Can use Spring annotations
- ✅ Orchestrates workflow
- ❌ NO business logic (delegate to domain!)
- ✅ Transaction boundaries

**What's inside**:
- **Input Ports** (`port/in/`): Interfaces defining what the application CAN DO
  - `ProcessProjectIntakePort`: Process project intake use case
- **Output Ports** (`port/out/`): Interfaces defining what the application NEEDS
  - `ProjectIntakeEventPublisher`: Publish events to external systems
- **Services** (`service/`): Implement input ports, orchestrate workflow
  - `ProcessProjectIntakeService`: Orchestrates validation, domain logic, and publishing
- **Config** (`config/`): Wire domain services as Spring beans
  - `DomainWiringConfig`: Creates domain service beans (keeps domain framework-free)

### Infrastructure Layer
**Location**: `infrastructure/`

**Purpose**: All framework-specific code and external system interactions

**Rules**:
- ✅ Framework annotations allowed
- ✅ DTOs for serialization/deserialization
- ✅ Mappers to convert DTO ↔ Domain
- ❌ NO business logic

**What's inside**:

#### Input Adapters (`adapter/in/`)
**Driving adapters** that receive external input and call input ports

- **REST** (`rest/`):
  - DTOs: `ProjectIntakeRequest`, `BudgetRangeRequest`
  - Mapper: `ProjectIntakeRequestMapper` (DTO → Domain)
  - Controller: `ProjectIntakeRestController`

**Flow**: REST Request → DTO → Mapper → Domain Model → Input Port

#### Output Adapters (`adapter/out/`)
**Driven adapters** that implement output ports and interact with external systems

- **Kafka** (`kafka/`):
  - DTOs: `ProjectIntakeEvent`, `BudgetRangeEvent`
  - Mapper: `ProjectIntakeEventMapper` (Domain → DTO)
  - Publisher: `KafkaProjectIntakePublisher` (implements `ProjectIntakeEventPublisher`)

**Flow**: Output Port → Domain Model → Mapper → DTO → Kafka

## Data Flow

```
┌─────────────────────────────────────────────────────────────────┐
│                        External System                          │
│                          (REST Client)                          │
└────────────────────┬────────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│                    INFRASTRUCTURE LAYER                         │
│  ┌───────────────────────────────────────────────────────────┐ │
│  │ Input Adapter: ProjectIntakeRestController                │ │
│  │ - Receives ProjectIntakeRequest (DTO)                     │ │
│  └───────────────────┬───────────────────────────────────────┘ │
│                      │                                           │
│  ┌───────────────────▼───────────────────────────────────────┐ │
│  │ Mapper: ProjectIntakeRequestMapper                        │ │
│  │ - Converts DTO → ProjectIntake (Domain)                   │ │
│  └───────────────────┬───────────────────────────────────────┘ │
└────────────────────────────────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────────┐
│                     APPLICATION LAYER                           │
│  ┌───────────────────────────────────────────────────────────┐ │
│  │ Input Port: ProcessProjectIntakePort.process()            │ │
│  └───────────────────┬───────────────────────────────────────┘ │
│                      │                                           │
│  ┌───────────────────▼───────────────────────────────────────┐ │
│  │ Application Service: ProcessProjectIntakeService          │ │
│  │ - Orchestrates workflow                                   │ │
│  │ - Calls domain validation                                 │ │
│  │ - Calls domain services                                   │ │
│  │ - Calls output ports                                      │ │
│  └───────────┬──────────────────────────┬────────────────────┘ │
└────────────────────────────────────────────────────────────────┘
               │                           │
               ▼                           ▼
┌──────────────────────────┐   ┌──────────────────────────────────┐
│     DOMAIN LAYER         │   │      APPLICATION LAYER           │
│  ┌────────────────────┐  │   │  ┌────────────────────────────┐ │
│  │ Domain Model:      │  │   │  │ Output Port:               │ │
│  │ ProjectIntake      │  │   │  │ ProjectIntakeEventPublisher│ │
│  │ - validate()       │  │   │  └──────────┬─────────────────┘ │
│  │ - business logic   │  │   └────────────────────────────────┘
│  └────────────────────┘  │                │
│                          │                ▼
│  ┌────────────────────┐  │   ┌──────────────────────────────────┐
│  │ Domain Service:    │  │   │    INFRASTRUCTURE LAYER          │
│  │ ProjectIntakeDomain│  │   │  ┌────────────────────────────┐ │
│  │ Service            │  │   │  │ Output Adapter:            │ │
│  │ - complex rules    │  │   │  │ KafkaProjectIntakePublisher│ │
│  └────────────────────┘  │   │  │ - Implements output port   │ │
└──────────────────────────┘   │  └──────────┬─────────────────┘ │
                               │             │                    │
                               │  ┌──────────▼─────────────────┐ │
                               │  │ Mapper:                    │ │
                               │  │ ProjectIntakeEventMapper   │ │
                               │  │ - Domain → DTO             │ │
                               │  └──────────┬─────────────────┘ │
                               └────────────────────────────────┘
                                            │
                                            ▼
                               ┌──────────────────────────┐
                               │   External System        │
                               │   (Kafka Topic)          │
                               └──────────────────────────┘
```

## Key Architectural Principles

### Dependency Rule
Dependencies flow **inward only**:
```
Infrastructure → Application → Domain
```
- Infrastructure depends on Application and Domain
- Application depends on Domain
- **Domain depends on NOTHING** (pure Kotlin/Java)

### Port Types

| Port Type | Location | Purpose | Implemented By | Called By |
|-----------|----------|---------|----------------|-----------|
| **Input Port** | `application/port/in/` | Define what app CAN DO | Application Services | Input Adapters |
| **Output Port** | `application/port/out/` | Define what app NEEDS | Output Adapters | Application Services |

### Naming Conventions

| Type | Pattern | Example |
|------|---------|---------|
| Input Port | `{UseCase}Port` | `ProcessProjectIntakePort` |
| Output Port | `{Entity}{Action}` | `ProjectIntakeEventPublisher` |
| Input Adapter | `{Entity}RestController` | `ProjectIntakeRestController` |
| Output Adapter | `Kafka{Entity}Publisher` | `KafkaProjectIntakePublisher` |
| Domain Service | `{Entity}DomainService` | `ProjectIntakeDomainService` |
| Application Service | `{UseCase}Service` | `ProcessProjectIntakeService` |

## Business Logic Examples

### Domain Model - ProjectIntake
```kotlin
// Validation
fun validate()

// Business Rules
fun isHighPriority(): Boolean               // Budget >= 100K or pain points >= 5
fun getCategory(): String                   // ENTERPRISE, PREMIUM, STANDARD, BASIC
fun requiresSeniorReview(): Boolean         // Based on priority and category
fun getEstimatedResponseTime(): Int         // 4-48 hours based on category
fun isComplete(): Boolean                   // All required fields present and valid
```

### Domain Model - BudgetRange
```kotlin
// Validation
fun validate()

// Business Rules
fun isValid(): Boolean
fun formatForDisplay(): String              // "USD 10,000 - 50,000"
fun getBudgetCategory(): String             // LARGE, MEDIUM, SMALL, MICRO
fun getAverageBudget(): Int
```

### Domain Service - ProjectIntakeDomainService
```kotlin
// Multi-entity business logic
fun assignReviewTeam(projectIntake): String
fun shouldAutoApproveConsultation(projectIntake): Boolean
fun calculatePriorityScore(projectIntake): Int
fun requiresComplianceReview(projectIntake): Boolean
```

## Technologies

- **Language**: Kotlin
- **Framework**: Spring Boot 4.0.2
- **Build Tool**: Gradle
- **Java Version**: 21+
- **Messaging**: Spring Kafka
- **API Documentation**: SpringDoc OpenAPI
- **Logging**: Kotlin Logging

## API Endpoints

### Submit Project Intake
```
POST /api/v1/project-intakes
```

**Request Body**:
```json
{
  "clientName": "Acme Corp",
  "industry": "Technology",
  "companySize": "50-200",
  "context": "Need help with digital transformation",
  "painPoints": [
    "Legacy systems",
    "Slow processes",
    "Poor user experience"
  ],
  "budgetRange": {
    "min": 50000,
    "max": 150000,
    "currency": "USD"
  },
  "additionalNotes": "Urgent project"
}
```

**Response**: `202 Accepted`

## Configuration

### Application Configuration
See `src/main/resources/application.yml`:
```yaml
server:
  port: 8081
  servlet:
    context-path: /api/v1
```

### CORS Configuration
See `CorsConfig.kt` - Enables CORS for all endpoints

### Jackson Configuration
See `JacksonConfig.kt` - Configures JSON serialization with Kotlin module

### OpenAPI Configuration
See `OpenApiConfig.kt` - Configures Swagger UI

Access Swagger UI at: `http://localhost:8081/api/v1/swagger-ui.html`

## Running the Application

### Build
```bash
./gradlew clean build
```

### Run
```bash
./gradlew bootRun
```

### Test
```bash
./gradlew test
```

## Benefits of This Architecture

### ✅ Testability
- Domain logic testable without Spring or Kafka
- Application services testable with mocked ports
- Adapters testable independently

### ✅ Maintainability
- Clear separation of concerns
- Business logic isolated from infrastructure
- Changes to infrastructure don't affect domain

### ✅ Flexibility
- Easy to swap Kafka for RabbitMQ
- Can add GraphQL alongside REST
- Multiple adapters can share the same ports

### ✅ Technology Independence
- Domain has no framework coupling
- Can migrate frameworks without rewriting business logic
- Infrastructure changes isolated to adapter layer

## Future Enhancements (TODOs in code)

1. **Database Integration**:
   - Add JPA repository output adapter
   - Implement persistence for project intakes
   - Check for duplicate submissions

2. **Kafka Configuration**:
   - Configure Kafka topics
   - Enable actual message publishing
   - Add error handling and retry logic

3. **Additional Features**:
   - Authentication & authorization
   - Rate limiting
   - Async processing with status tracking
   - Email notifications

## References

- **Hexagonal Architecture**: Alistair Cockburn
- **Clean Architecture**: Robert C. Martin
- **Domain-Driven Design**: Eric Evans
- **INSTRUCTIONS.md**: Detailed implementation guide for replicating this architecture

---

**Last Updated**: 2026-02-16
**Architecture**: Hexagonal (Ports and Adapters)
**Pattern Compliance**: ✅ Full compliance with INSTRUCTIONS.md
