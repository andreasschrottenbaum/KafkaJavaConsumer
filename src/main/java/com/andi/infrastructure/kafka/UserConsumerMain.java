package com.andi.infrastructure.kafka;

import com.andi.logic.AdultCheckStrategy;
import com.andi.logic.HighTrustUserStrategy;
import com.andi.logic.LogUserNameStrategy;
import com.andi.logic.UserAgeStrategy;
import com.andi.domain.User;

import org.apache.kafka.common.serialization.StringDeserializer;

/**
 * The main entry point for the User-specific Kafka Consumer application.
 * <p>
 * This class demonstrates a sophisticated consumer setup using a resilient
 * {@link ErrorHandlingDeserializer} to map JSON payloads to {@link User} records.
 * It leverages the {@link StreamProcessor} to execute a pipeline of multiple
 * processing strategies in parallel.
 * </p>
 */
public class UserConsumerMain {

    /**
     * Bootstraps the Kafka consumer, initializes the strategy pipeline,
     * and starts the multi-threaded processing loop.
     * <p>
     * Instead of a single strategy, this implementation uses a Lambda expression
     * to iterate over a list of {@code MessageProcessor} implementations for
     * every incoming record, effectively creating a processing chain.
     * </p>
     */
    void main()
    {
        // Set up resilient deserialization for User domain objects
        var errorHandlingDeserializer = new ErrorHandlingDeserializer<>(User.class);

        // Create consumer via centralized factory
        var consumer = KafkaConsumerFactory.createConsumer(
            "user-consumer",
            new StringDeserializer(),
            errorHandlingDeserializer
        );

        // Define the processing pipeline
        var strategies = java.util.List.of(
            new LogUserNameStrategy(),
            new HighTrustUserStrategy(),
            new UserAgeStrategy(),
            new AdultCheckStrategy()
        );

        // Initialize the processor with a Lambda to handle the strategy list
        var processor = new StreamProcessor<>(consumer, "user-events", (user) -> {
            for (var s : strategies) {
                s.process(user);
            }
        });

        // Start the infinite polling and execution loop
        processor.start();
    }
}