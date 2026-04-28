package com.andreas.infrastructure;

import com.andreas.domain.MessageEmitter;

/**
 * Basic implementation of {@link MessageEmitter} that outputs messages to the standard console.
 * <p>
 * This class serves as the production implementation for local monitoring and
 * simple command-line feedback.
 * </p>
 */
public class ConsoleEmitter implements MessageEmitter {

    /**
     * Prints the given message followed by a line break to {@code System.out}.
     * @param message The string content to be displayed in the console.
     */
    @Override
    public void emit(String message) {
        System.out.println(message);
    }
}