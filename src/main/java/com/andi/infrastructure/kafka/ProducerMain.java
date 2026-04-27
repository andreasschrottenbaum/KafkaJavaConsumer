package com.andi.infrastructure.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class ProducerMain {
    public static void main(String[] args) {
        Properties props = new Properties();

        props.put("bootstrap.servers", "172.28.166.143:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            ProducerRecord<String, String> record = new ProducerRecord<>("test-events", "key-2", "Hello From KafkaPlayground2!");

            try {
                var metadata = producer.send(record).get();
                System.out.println("topic: " + metadata.topic() + ", partition:" + metadata.partition());
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
