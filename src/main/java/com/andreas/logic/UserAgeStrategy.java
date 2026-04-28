package com.andreas.logic;

import com.andreas.domain.MessageProcessor;
import com.andreas.domain.User;

import java.time.LocalDate;
import java.time.Period;

/**
 * Strategy for calculating and displaying user age.
 * <p>
 * This strategy computes the chronological age of a user by comparing
 * their {@code birthDate} with the current system date.
 * </p>
 */
public class UserAgeStrategy implements MessageProcessor<User> {

    /**
     * Processes the user record by calculating their age and printing it
     * alongside their birth date to the console.
     * @param user The user record to process.
     */
    @Override
    public void process(User user) {
        System.out.println("Age: " + getAge(user) + " (" + user.birthDate() + ")");
    }

    /**
     * Calculates the chronological age in years based on the user's birth date
     * relative to the current system date.
     * @param user The user whose age is to be calculated.
     * @return The number of full years elapsed since the birth date.
     */
    public int getAge(User user) {
        var currentDate = LocalDate.now();
        return Period.between(user.birthDate(), currentDate).getYears();
    }
}