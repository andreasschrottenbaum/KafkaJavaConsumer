package com.andreas.infrastructure.kafka;

import com.andreas.domain.MessageProcessor;
import org.apache.kafka.clients.consumer.Consumer;
import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.Executors;

/**
 * A generic engine for consuming and processing Kafka message streams.
 * <p>
 * The {@code StreamProcessor} decouples the Kafka polling infrastructure from
 * the actual message processing logic. It manages its own thread pool to
 * execute {@link MessageProcessor} tasks concurrently, ensuring that the
 * main polling loop remains responsive.
 * </p>
 *
 * @param <K> The type of the Kafka message key.
 * @param <V> The type of the Kafka message value (payload).
 */
public class StreamProcessor<K, V> {
    private final Consumer<K, V> consumer;
    private final String topic;
    private final MessageProcessor<V> processor;
    private volatile boolean running = true;

    /**
     * Constructs a new StreamProcessor.
     *
     * @param consumer  The pre-configured {@link Consumer} instance.
     * @param topic     The name of the Kafka topic to subscribe to.
     * @param processor The implementation of {@link MessageProcessor} defining
     * what happens with each message.
     */
    public StreamProcessor(Consumer<K, V> consumer, String topic, MessageProcessor<V> processor) {
        this.consumer = consumer;
        this.topic = topic;
        this.processor = processor;
    }

    /**
     * Starts the infinite polling loop and initializes the worker thread pool.
     * Also launches a virtual thread to monitor 'System.in' for a manual shutdown signal ('q').
     * <p>
     * This method uses a try-with-resources statement to ensure that both the
     * {@code Consumer} and the {@code ExecutorService} are gracefully
     * shut down when the loop terminates. Processing is offloaded to a
     * fixed thread pool based on the available system processors.
     * </p>
     */
    public void start() {
        Thread.startVirtualThread(() -> {
            System.out.println("Press 'q' and Enter to stop the consumer...");
            try (var scanner = new java.util.Scanner(System.in)) {
                while (true) {
                    if (scanner.hasNextLine() && scanner.nextLine().equalsIgnoreCase("q")) {
                        System.out.println("Shutting down...");
                        stop();
                        break;
                    }
                }
            }
        });


        var executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        try (consumer; executorService) {
            consumer.subscribe(Collections.singletonList(topic));

            while (running) {
                try {
                    var records = consumer.poll(Duration.ofMillis(100));
                    if (records == null) continue;

                    for (var record : records) {
                        V value = record.value();
                        if (value == null) continue;

                        // Offload processing to the thread pool
                        executorService.submit(() -> {
                            try {
                                processor.process(value);
                            } catch (Exception e) {
                                System.err.println("Processing error on topic " + topic + ": " + e.getMessage());
                            }
                        });
                    }
                } catch (org.apache.kafka.common.errors.WakeupException e) {
                    if (running) throw e;
                }
            }
        }
        System.out.println("Processor for " + topic + " stopped cleanly.");
    }

    /**
     * Signals the polling loop to stop gracefully.
     * <p>
     * The loop will finish its current poll/process cycle before terminating.
     * </p>
     */
    public void stop() {
        this.running = false;
        consumer.wakeup();
    }
}