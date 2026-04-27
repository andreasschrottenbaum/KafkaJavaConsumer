package com.andi.logic;

import com.andi.domain.User;
import com.andi.domain.UserProcessingStrategy;

public class HighTrustStrategy implements UserProcessingStrategy {
    @Override
    public void process(User user) {
        if (user.trustLevel() > 8) {
            System.out.println("🌟 Trusted User: ID" + user.id() +" 🌟");
        }
    }
}
