package com.andreas.infrastructure.kafka;

import com.andreas.domain.MessageProcessor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.anyString;

@ExtendWith(MockitoExtension.class)
public class StreamProcessorTest {
    @Mock
    private Consumer<String, String> kafkaConsumerMock;

    @Mock
    private MessageProcessor<String> strategyMock;

    private StreamProcessor<String, String> processor;

    @BeforeEach
    void setUp() {
        processor = new StreamProcessor<>(kafkaConsumerMock, "test-topic", strategyMock);
    }

    @Test
    @DisplayName("Should process records and then stop")
    void testProcessing() {
        var record = new ConsumerRecord<>("test-topic", 0, 0L, "key", "value");
        var partition = new TopicPartition("test-topic", 0);

        @SuppressWarnings("deprecation")
        var records = new ConsumerRecords<>(Map.of(
            partition,
            List.of(record)
        ));

        when(kafkaConsumerMock.poll(any()))
            .thenReturn(records)
            .thenThrow(new RuntimeException("Stop Loop"));

        try {
            processor.start();
        } catch (Exception e) {
            // Quit test
        }

        verify(strategyMock, timeout(1000)).process("value");
    }

    @Test
    @DisplayName("Should continue processing even if strategy throws an exception")
    void testErrorHandling() {
        var record1 = new ConsumerRecord<>("test-topic", 0, 0L, "key1", "value1");
        var record2 = new ConsumerRecord<>("test-topic", 0, 1L, "key2", "value2");
        var partition = new TopicPartition("test-topic", 0);

        @SuppressWarnings("deprecation")
        var records = new ConsumerRecords<>(Map.of(partition, List.of(record1, record2)));

        doThrow(new RuntimeException("Boom!"))
                .doNothing()
                .when(strategyMock).process(anyString());

        when(kafkaConsumerMock.poll(any()))
                .thenReturn(records)
                .thenThrow(new RuntimeException("Stop Loop"));

        try {
            processor.start();
        } catch (Exception e) {
            // Quit test
        }

        verify(strategyMock, timeout(1000)).process("value1");
        verify(strategyMock, timeout(1000)).process("value2");
    }
}
