package com.andreas.domain;

/**
 * Functional interface for processing messages consumed from Kafka.
 * <p>
 * This interface serves as the primary abstraction for message handling logic.
 * By using generics, it remains type-safe for any payload (e.g., {@code User} objects
 * or {@code String} messages) and can be easily implemented via Lambda expressions
 * or Method References.
 * </p>
 *
 * @param <V> The type of the message value to be processed.
 */
@FunctionalInterface
public interface MessageProcessor<V> {

    /**
     * Executes the processing logic for a single message value.
     * <p>
     * Implementation should be thread-safe if the processor is used
     * within a multi-threaded {@code StreamProcessor} environment.
     * </p>
     *
     * @param value The message payload to process.
     */
    void process(V value);
}