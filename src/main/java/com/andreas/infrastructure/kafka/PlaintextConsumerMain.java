package com.andreas.infrastructure.kafka;

import com.andreas.logic.PlaintextStrategy;
import org.apache.kafka.common.serialization.StringDeserializer;

/**
 * Entry point for the plaintext message consumer application.
 * <p>
 * This class demonstrates a minimal consumer implementation using the
 * {@link KafkaConsumerFactory} and {@link StreamProcessor}. It focuses on
 * consuming raw string data from the "plaintext-events" topic and
 * processing it via a single strategy.
 * </p>
 */
public class PlaintextConsumerMain {

    /**
     * Bootstraps the plaintext consumer and begins the streaming process.
     * <p>
     * Uses standard {@link StringDeserializer} for both key and value as no
     * complex object mapping is required for this stream.
     * </p>
     */
    void main() {
        // Initialize consumer via factory with dedicated group-id
        var consumer = KafkaConsumerFactory.createConsumer(
            "plaintext-consumer",
            new StringDeserializer(),
            new StringDeserializer()
        );

        var strategy = new PlaintextStrategy();

        // Create and start the stream processor using a method reference for the strategy
        var processor = new StreamProcessor<>(
            consumer,
            "plaintext-events",
            strategy
        );

        processor.start();
    }
}