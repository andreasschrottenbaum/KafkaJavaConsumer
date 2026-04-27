# KafkaJavaConsumer

A modern Java-based Kafka consumer demonstration project focusing on high-performance parallel processing, clean architecture, and resilient deserialization.

## Overview

This project serves as a learning playground for advanced Java features (Java 26+) and Apache Kafka. It demonstrates how to decouple message consumption from business logic using the Strategy Pattern and the ExecutorService for concurrent execution.

## Features

- **Concurrent Processing**: Uses a FixedThreadPool to process multiple Kafka records in parallel.
- **Dual Consumer Architecture**:

  - `UserConsumerMain`: Specialized for structured JSON data (User objects).
  - `PlaintextConsumerMain`: A general-purpose consumer for raw string data.
- **Resilient Deserialization**: A custom ErrorHandlingDeserializer prevents "Poison Pills" from stalling the consumer group.
- **Modern Java**: Utilizes Java 26 features like instance main methods and unnamed parameters.
- **Strategy Pattern**: Decoupled logic steps (Logging, Age Calculation, Trust Filtering).

## Prerequisites

Before running the application, ensure you have the following:

- **Java 26** (or latest Preview).
- **Maven** for dependency management.
- **Apache Kafka** instance running and accessible.
- **Bun** (optional, if using the associated TypeScript producer https://github.com/andreasschrottenbaum/KafkaTSProducer).

## Configuration

### Network & IP Settings
The consumer is currently configured to connect to a specific Kafka broker. **You must update the IP address** in `UserConsumerMain.java` and `PlaintextConsumerMain.java` to match your environment:

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
│   ├── User.java                     // User Data Model (Record)
│   ├── UserProcessingStrategy.java    // Interface for JSON logic
│   └── PlaintextProcessStrategy.java  // Interface for String logic
├── infrastructure
│   └── kafka
│       ├── ConsumerMain.java          // Entry point: JSON Consumer
│       ├── PlaintextConsumerMain.java // Entry point: String Consumer
│       └── ErrorHandlingDeserializer.java // Resilient JSON parsing
└── logic
    ├── HighTrustStrategy.java        // Business logic filter
    ├── LogUserStrategy.java          // Standard logging
    ├── UserAgeStrategy.java          // Date/Time processing
    └── PlaintextStrategy.java        // Simple string processing
```

## Getting Started
1. Clone the repository.
2. Update the BOOTSTRAP_SERVERS_CONFIG in `UserConsumerMain.java` and `PlaintextConsumerMain.java`.
3. Build the project:
    ```bash
    mvn clean install
    ```
4. Run the Consumers.

You can run both Main classes simultaneously in your IDE to listen on different topics:
 - `user-events` (JSON, `UserConsumerMain`)
 - `plaintext-events` (Raw Strings, `PlaintextConsumerMain`)

## Technical Deep Dive: Parallel Execution
The consumers decouple the Kafka polling loop from the message processing. While the main thread keeps polling for new data, an ExecutorService manages a pool of worker threads to execute the strategies. This ensures that slow processing logic doesn't block the Kafka heartbeats.

## License
MIT
