package com.andreas.domain;

/**
 * Port for outgoing messages.
 * Decouples the logic from the actual output technology (Console, Kafka, Database).
 */
public interface MessageEmitter {
    void emit(String message);
}
