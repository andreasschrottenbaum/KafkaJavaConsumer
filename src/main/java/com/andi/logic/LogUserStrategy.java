package com.andi.logic;

import com.andi.domain.User;
import com.andi.domain.UserProcessingStrategy;

public class LogUserStrategy implements UserProcessingStrategy {
    @Override
    public void process(User user)
    {
        System.out.println("LOG: processing user " + user.firstName() + " " + user.lastName());
    }
}
