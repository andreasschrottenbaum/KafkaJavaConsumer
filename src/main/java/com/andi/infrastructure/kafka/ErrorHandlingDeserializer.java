package com.andi.infrastructure.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.kafka.common.serialization.Deserializer;

/**
 * A resilient Kafka deserializer that converts JSON byte arrays into Java objects.
 * <p>
 * Unlike standard deserializers, this implementation catches exceptions during
 * the parsing process to prevent the Kafka Consumer Group from crashing
 * (Poison Pill protection). Invalid records are logged to {@code System.err}
 * and return {@code null}.
 * </p>
 *
 * @param <T> The target type this deserializer will produce.
 */
public class ErrorHandlingDeserializer<T> implements Deserializer<T> {
    private final ObjectMapper mapper;
    private final Class<T> targetType;

    /**
     * Initializes the Jackson {@link ObjectMapper} with necessary modules
     * for Java 8 Date/Time support and ISO-8601 compatibility.
     *
     * @param targetType The class literal of the target type (required due to Type Erasure).
     */
    public ErrorHandlingDeserializer(Class<T> targetType) {
        this.targetType = targetType;

        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Deserializes a Kafka message payload.
     * * @param topic The Kafka topic the message belongs to.
     * @param data  The raw byte array from Kafka.
     * @return An instance of {@code T}, or {@code null} if deserialization fails.
     */
    @Override
    public T deserialize(String topic, byte[] data) {
        if (data == null) return null;

        try {
            return mapper.readValue(data, targetType);
        } catch (Exception e) {
            System.err.println("Invalid entry detected in topic " + topic + "!");
            System.err.println("Reason: " + e.getMessage());
            return null;
        }
    }
}
