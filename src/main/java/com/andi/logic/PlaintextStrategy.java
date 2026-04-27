package com.andi.logic;

import com.andi.domain.MessageProcessor;

/**
 * A simple implementation of {@link MessageProcessor} for raw string data.
 * <p>
 * This strategy serves as a basic handler that outputs the incoming
 * plaintext messages directly to the system console. It is primarily used
 * for debugging and monitoring the "plaintext-events" stream.
 * </p>
 */
public class PlaintextStrategy implements MessageProcessor<String> {
    @Override
    public void process(String input) {
        System.out.println(input);
    }
}