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
    @Override
    public void process(User user) {
        if (user.trustLevel() > 8) {
            System.out.println("🌟 Trusted User: ID" + user.id() +" 🌟");
        }
    }
}
