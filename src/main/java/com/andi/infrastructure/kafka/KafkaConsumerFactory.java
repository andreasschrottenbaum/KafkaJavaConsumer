package com.andi.infrastructure.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Properties;

/**
 * Factory class for creating and configuring {@link KafkaConsumer} instances.
 * <p>
 * This utility centralizes the Kafka broker configuration and ensures consistent
 * consumer settings across the application. It simplifies the instantiation of
 * consumers by abstracting boilerplate property setup.
 * </p>
 */
public class KafkaConsumerFactory {

    /**
     * The fixed IP address and port of the Kafka broker.
     */
    private static final String KAFKA_IP = "172.28.166.143:9092";

    /**
     * Creates a pre-configured {@link KafkaConsumer} with the specified parameters.
     * <p>
     * The consumer is configured with {@code AUTO_OFFSET_RESET_CONFIG} set to "earliest"
     * to ensure all available messages in a topic are processed if no offset is committed.
     * </p>
     *
     * @param <K>     The type of the message key.
     * @param <V>     The type of the message value.
     * @param groupId The unique identifier for the consumer group.
     * @param keyDS   The deserializer implementation for the message keys.
     * @param valueDS The deserializer implementation for the message values.
     * @return A fully initialized {@code KafkaConsumer} instance.
     */
    public static <K, V> KafkaConsumer<K, V> createConsumer(String groupId, Deserializer<K> keyDS, Deserializer<V> valueDS) {
        var props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_IP);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new KafkaConsumer<>(props, keyDS, valueDS);
    }
}