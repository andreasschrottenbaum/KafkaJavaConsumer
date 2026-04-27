package com.andi.logic;

import com.andi.domain.User;
import com.andi.domain.UserProcessingStrategy;

import java.time.LocalDate;
import java.time.Period;

public class UserAgeStrategy implements UserProcessingStrategy {
    @Override
    public void process(User user) {
        var currentDate = LocalDate.now();
        var age = Period.between(user.birthDate(), currentDate).getYears();

        System.out.println("Age: " + age + "(" + user.birthDate() + ")");
    }
}
