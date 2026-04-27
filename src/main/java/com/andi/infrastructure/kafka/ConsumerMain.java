package com.andi.infrastructure.kafka;

import com.andi.logic.HighTrustStrategy;
import com.andi.logic.LogUserStrategy;
import com.andi.logic.UserAgeStrategy;
import com.andi.domain.User;
import com.andi.domain.UserProcessingStrategy;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

public class ConsumerMain {
    public static void main(String[] args)
    {
        Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.28.166.143:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "java-playground-consumer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        var errorHandlingDeserializer = new ErrorHandlingDeserializer<>(User.class);

        var strategies = new ArrayList<UserProcessingStrategy>();
        strategies.add(new LogUserStrategy());
        strategies.add(new HighTrustStrategy());
        strategies.add(new UserAgeStrategy());

        try (KafkaConsumer<String, User> consumer = new KafkaConsumer<>(props, new StringDeserializer(), errorHandlingDeserializer)) {
            consumer.subscribe(Collections.singletonList("test-events"));

            while (true) {
                var records = consumer.poll(Duration.ofMillis(100));

                if (records == null) continue;

                for (var record : records) {
                    var user = record.value();
                    if (user == null) continue;

                    for (var strategy : strategies) {
                        strategy.process(user);
                    }
                }
            }
        }
    }
}
