package com.andreas.logic;

import com.andreas.domain.MessageEmitter;
import com.andreas.domain.MessageProcessor;
import com.andreas.domain.User;

/**
 * Standard logging strategy for incoming user records.
 * <p>
 * Simple implementation that prints basic user information (name and ID)
 * to the standard output for monitoring purposes.
 * </p>
 */
public class LogUserNameStrategy implements MessageProcessor<User> {
    private final MessageEmitter emitter;

    public LogUserNameStrategy(MessageEmitter emitter) {
        this.emitter = emitter;
    }

    /**
     * Processes the user record by logging the full name to the console.
     * @param user The user record to be logged.
     */
    @Override
    public void process(User user) {
        emitter.emit("LOG: processing user " + getName(user));
    }

    /**
     * Concatenates the first and last name of a user.
     * @param user The user record containing name components.
     * @return A single string containing the full name.
     */
    String getName(User user) {
        return user.firstName() + " " + user.lastName();
    }
}