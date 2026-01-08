Transaction Risk Monitor
A high-performance financial transaction monitoring and risk assessment system built with Spring Boot 4.0 and Java 21.
This project implements a robust architecture for transaction processing, audit logging, and automated risk analysis using advanced annotation processing for efficient code generation.

Technical Stack
Java 21 & Spring Boot 4.0.1
Spring Data JPA: Data persistence and ORM.
PostgreSQL: Primary relational database.
Docker & Docker Compose: Infrastructure containerization.
MapStruct: Compile-time mapper generation (DTO <-> Entity).
Lombok: Automated boilerplate reduction.
Jakarta Validation: Data integrity and constraint enforcement.

Project Architecture
The application follows a clean, layered architecture to ensure separation of concerns:
Controller: API endpoints and request handling.
Service: Business logic orchestration and transaction management.
Model: JPA Entities representing the persistence schema.
DTO (Data Transfer Objects): Validated data structures for API contracts.
Mapper: MapStruct interfaces for type-safe object conversion.
Repository: Data access abstraction layer.
Enums: Domain definitions for Status, Types, and Risk Levels.


Installation and Execution
Infrastructure Setup
The database environment is managed via Docker. Navigate to the database directory and initialize the container:
cd docker/database
docker-compose up -d

Compilation and Code Generation
This project utilizes the maven-compiler-plugin to trigger MapStruct and Lombok processors. Execute the following to generate implementation classes:



mvn clean compile
Generated sources are located in target/generated-sources/annotations.

Running the Application:
mvn spring-boot:run

Database Schema Management
The application uses Hibernate's DDL-auto feature set to update. Upon startup, the system will:
Verify the existence of tables defined in the model package.
Create missing tables and indexes.
Synchronize schema changes without dropping existing data.

Development Standards
External Identifiers: Use of UUID (external_id) for public API exposure to prevent internal ID enumeration.
Audit Lifecycle: Automated timestamps and history tracking via JPA @PrePersist and @PreUpdate hooks.
Validation: Strict enforcement of financial constraints (positive amounts, ISO currency codes) at the entry point.