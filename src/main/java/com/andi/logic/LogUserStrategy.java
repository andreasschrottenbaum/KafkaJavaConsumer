package com.andi.logic;

import com.andi.domain.MessageProcessor;
import com.andi.domain.User;

/**
 * Standard logging strategy for incoming user records.
 * <p>
 * Simple implementation that prints basic user information (name and ID)
 * to the standard output for monitoring purposes.
 * </p>
 */
public class LogUserStrategy implements MessageProcessor<User> {
    @Override
    public void process(User user)
    {
        System.out.println("LOG: processing user " + user.firstName() + " " + user.lastName());
    }
}
