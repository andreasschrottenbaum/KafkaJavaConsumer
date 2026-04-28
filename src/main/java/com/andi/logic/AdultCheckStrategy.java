package com.andi.logic;

import com.andi.domain.MessageProcessor;
import com.andi.domain.User;

import java.time.LocalDate;
import java.time.Period;

/**
 * Strategy for calculating and displaying user age.
 * <p>
 * This strategy computes the chronological age of a user by comparing
 * their {@code birthDate} with the current system date.
 * </p>
 */
public class AdultCheckStrategy implements MessageProcessor<User> {
    @Override
    public void process(User user) {
        System.out.println(formatOutput(user));
    }

    String formatOutput(User user)
    {
        var currentDate = LocalDate.now();
        var age = Period.between(user.birthDate(), currentDate).getYears();
        var state = determineState(age);

        return "User " + user.firstName() + " " + user.lastName() + " is " + state;
    }

    private String determineState(int age) {
        if (age < 10) return "a kid";
        if (age < 18) return "a minor";
        if (age < 21) return "a young adult";
        return "an adult";
    }
}
