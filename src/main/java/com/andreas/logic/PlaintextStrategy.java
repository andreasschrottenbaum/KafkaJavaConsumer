package com.andreas.logic;

import com.andreas.domain.MessageEmitter;
import com.andreas.domain.MessageProcessor;

/**
 * A simple implementation of {@link MessageProcessor} for raw string data.
 * <p>
 * This strategy serves as a basic handler that outputs the incoming
 * plaintext messages directly to the system console. It is primarily used
 * for debugging and monitoring the "plaintext-events" stream.
 * </p>
 */
public class PlaintextStrategy implements MessageProcessor<String> {
    private final MessageEmitter emitter;

    public PlaintextStrategy(MessageEmitter emitter) {
        this.emitter = emitter;
    }

    /**
     * Processes a raw string message by printing it directly to the console.
     * @param input The plaintext message received from the stream.
     */
    @Override
    public void process(String input) {
        emitter.emit(input);
    }
}