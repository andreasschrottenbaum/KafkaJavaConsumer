package com.andi.infrastructure.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.kafka.common.serialization.Deserializer;

public class ErrorHandlingDeserializer<T> implements Deserializer<T> {
    private ObjectMapper mapper;
    private Class<T> targetType;

    public ErrorHandlingDeserializer(Class<T> targetType) {
        this.targetType = targetType;

        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

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
