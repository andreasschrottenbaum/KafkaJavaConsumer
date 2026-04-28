package com.andi.logic;

import com.andi.domain.MessageProcessor;
import com.andi.domain.User;

/**
 * Strategy that filters for highly trusted users.
 * <p>
 * This implementation identifies users with a {@code trustLevel} above 8
 * and highlights them in the console.
 * </p>
 */
public class HighTrustUserStrategy implements MessageProcessor<User> {

    /**
     * Processes the user record and prints a special highlight to the console
     * if the user meets the trust requirements.
     * * @param user The user record to be evaluated.
     */
    @Override
    public void process(User user) {
        if (isTrusted(user)) {
            System.out.println("🌟 Trusted User: ID " + user.id() +" 🌟");
        }
    }

    /**
     * Evaluates if a user is considered "highly trusted".
     * A user is trusted if their numerical trust level is strictly greater than 8.
     * * @param user The user to check.
     * @return {@code true} if the trust level is 9 or higher; {@code false} otherwise.
     */
    public boolean isTrusted(User user) {
        return user.trustLevel() > 8;
    }
}