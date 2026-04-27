package com.andi.infrastructure.kafka;

import com.andi.logic.PlaintextStrategy;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class PlaintextConsumerMain {
    volatile boolean running = true;

    void main() {
        Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.28.166.143:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "plaintext-consumer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        var plaintextStrategy = new PlaintextStrategy();

        var consumer = new KafkaConsumer<String, String>(props);
        var executorService = java.util.concurrent.Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        try (consumer; executorService) {
            consumer.subscribe(Collections.singletonList("plaintext-events"));

            while (running) {
                var records = consumer.poll(Duration.ofMillis(100));
                if (records == null) continue;

                for (var record : records) {
                    executorService.submit(() -> {
                        try {
                            plaintextStrategy.process(record.value());
                        } catch (Exception e) {
                            System.err.println("Error while parsing message. " + e.getMessage());
                        }
                    });
                }
            }
        }
    }
}
