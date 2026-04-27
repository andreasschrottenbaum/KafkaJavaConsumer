# KafkaJavaConsumer

A Java-based Kafka consumer demonstration project focusing on clean architecture, decoupling through the Strategy Pattern, and resilient deserialization.

## Overview

This project serves as a learning playground for integrating Java with Apache Kafka. It features a custom `ErrorHandlingDeserializer` to handle "Poison Pills" (invalid JSON payloads) without crashing the consumer group. The business logic is decoupled using the Strategy Pattern, allowing for multiple processing steps (logging, age calculation, trust-level filtering) to run independently.

## Features

- **Custom Deserializer**: Uses Jackson to map JSON to Java Records.
- **Resilience**: Gracefully handles deserialization errors and logs the cause.
- **Strategy Pattern**: Decoupled logic for processing incoming `User` objects.
- **Domain-Driven Structure**: Clear separation between Infrastructure, Domain, and Logic.

## Prerequisites

Before running the application, ensure you have the following:

- **Java 17** or higher.
- **Maven** for dependency management.
- **Apache Kafka** instance running and accessible.
- **Bun** (optional, if using the associated TypeScript producer https://github.com/andreasschrottenbaum/KafkaTSProducer).

## Configuration

### Network & IP Settings
The consumer is currently configured to connect to a specific Kafka broker. **You must update the IP address** in `ConsumerMain.java` to match your environment:

```java
// infrastructure/kafka/ConsumerMain.java
props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "YOUR_KAFKA_IP:9092");
```

### Dependencies
Key libraries used in this project:

`kafka-clients`: Official Kafka Java client.
`jackson-databind`: JSON processing.
`jackson-datatype-jsr310`: Support for Java 8 Date/Time API.

## Project Structure
```
com.andi
├── domain
│   ├── User.java                     // Data model (Record)
│   └── UserProcessingStrategy.java    // Interface for logic
├── infrastructure
│   └── kafka
│       ├── ConsumerMain.java         // Entry point & Kafka setup
│       └── ErrorHandlingDeserializer.java // Resilient JSON parsing
└── logic
    ├── HighTrustStrategy.java        // Business logic filter
    ├── LogUserStrategy.java          // Standard logging
    └── UserAgeStrategy.java          // Date/Time processing
```

## Getting Started
1. Clone the repository.
2. Update the BOOTSTRAP_SERVERS_CONFIG in ConsumerMain.java.
3. Build the project:
    ```bash
    mvn clean install
    ```
4. Run the `ConsumerMain` class.

## License
MIT
