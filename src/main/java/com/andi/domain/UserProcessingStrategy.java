package com.andi.domain;

/**
 * Strategy interface for processing user data consumed from Kafka.
 * <p>
 * Implementations of this interface define specific business logic
 * to be executed for each valid User record.
 * </p>
 * <b>Note:</b> Since strategies may be executed within a thread pool,
 * implementations should be stateless or thread-safe.
 */
@FunctionalInterface
public interface UserProcessingStrategy {

    /**
     * Processes a single user record.
     *
     * @param user The user object to process. Guaranteed to be non-null
     * by the caller in the current architecture.
     */
    void process(User user);
}