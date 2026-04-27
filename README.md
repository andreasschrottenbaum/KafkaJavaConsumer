# KafkaJavaConsumer

A high-performance, modular Java-based Kafka consumer demonstration. This project showcases modern Java 26+ features, clean architecture through the **Strategy Pattern**, and a generic processing engine.

## Overview
This playground demonstrates how to build a resilient and scalable Kafka consumer. The core architecture decouples technical Kafka concerns (polling, threading, deserialization) from business logic (data processing strategies).

## Key Architectural Components

### 1. The Engine: `StreamProcessor<K, V>`
The heart of the application. It manages the Kafka polling loop and offloads message processing to a multi-threaded `ExecutorService`.
- **Concurrent**: Processes records in parallel using available CPU cores.
- **Generic**: Works with any data type, from raw Strings to complex Domain Objects.
- **Resilient**: Captures processing errors without stopping the stream.

### 2. Centralized Configuration: `KafkaConsumerFactory`
A factory that encapsulates the boilerplate setup of Kafka properties. It ensures that connection settings and consumer group configurations are consistent and centralized.

### 3. Business Logic: `MessageProcessor<V>`
A functional interface that allows for flexible processing strategies.
- **Strategy Pattern**: Implementations like `LogUserStrategy` or `UserAgeStrategy` can be swapped or chained easily.
- **Type-Safe**: Leveraging Java Generics to ensure compile-time safety for different payloads.

### 4. Resilience: `ErrorHandlingDeserializer`
A custom wrapper around Jackson that prevents "Poison Pill" messages from crashing the consumer group by gracefully handling malformed JSON.

## Features
- **Java 26 Ready**: Uses instance main methods and modern syntax.
- **Multi-Topic Support**: Separate entry points for `user-events` (JSON) and `plaintext-events` (String).
- **Clean Architecture**: Strict separation between Infrastructure, Domain, and Logic layers.

## Project Structure
```
com.andi
├── domain
│   ├── User.java                     // Domain Model (Record)
│   └── MessageProcessor.java         // Generic Strategy Interface
├── infrastructure
│   └── kafka
│       ├── KafkaConsumerFactory.java // Centralized Config
│       ├── StreamProcessor.java      // Parallel Execution Engine
│       ├── UserConsumerMain.java     // Entry Point (JSON)
│       ├── PlaintextConsumerMain.java// Entry Point (String)
│       └── ErrorHandlingDeserializer.java // Resilient Parsing
└── logic
├── HighTrustUserStrategy.java    // Logic implementation
├── LogUserStrategy.java          // Logic implementation
├── UserAgeStrategy.java          // Logic implementation
└── PlaintextStrategy.java        // Logic implementation
```

## Getting Started

1. **Build**:
   ```bash
   mvn clean install
   ```
2. **Configure**:
   Update the `KAFKA_IP` in `KafkaConsumerFactory.java`
3. Run:
   Run `UserConsumerMain` for structured User data.
   Run `PlaintextConsumerMain` for raw string data.

## License
MIT