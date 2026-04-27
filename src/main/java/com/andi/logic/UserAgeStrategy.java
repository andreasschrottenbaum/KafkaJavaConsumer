package com.andi.logic;

import com.andi.domain.User;
import com.andi.domain.UserProcessingStrategy;

import java.time.LocalDate;
import java.time.Period;

/**
 * Strategy for calculating and displaying user age.
 * <p>
 * This strategy computes the chronological age of a user by comparing
 * their {@code birthDate} with the current system date.
 * </p>
 */
public class UserAgeStrategy implements UserProcessingStrategy {
    @Override
    public void process(User user) {
        var currentDate = LocalDate.now();
        var age = Period.between(user.birthDate(), currentDate).getYears();

        System.out.println("Age: " + age + "(" + user.birthDate() + ")");
    }
}
