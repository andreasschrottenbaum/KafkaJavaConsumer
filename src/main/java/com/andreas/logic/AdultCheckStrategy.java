package com.andreas.logic;

import com.andreas.domain.MessageEmitter;
import com.andreas.domain.MessageProcessor;
import com.andreas.domain.User;

import java.time.Clock;
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
    private final MessageEmitter emitter;
    private final Clock clock;

    public AdultCheckStrategy(MessageEmitter emitter) {
        this.emitter = emitter;
        this.clock = Clock.systemDefaultZone();
    }

    public AdultCheckStrategy(MessageEmitter emitter, Clock clock) {
        this.emitter = emitter;
        this.clock = clock;
    }

    /**
     * Processes the user by printing the formatted age information to the console.
     * @param user The user record to process.
     */
    @Override
    public void process(User user) {
        emitter.emit(formatOutput(user));
    }

    /**
     * Formats the user's information and age category into a readable string.
     * @param user The user record containing names and birthDate.
     * @return A formatted string describing the user's current age state.
     */
    String formatOutput(User user) {
        var currentDate = LocalDate.now(clock);
        var age = Period.between(user.birthDate(), currentDate).getYears();
        var state = determineState(age);

        return "User " + user.firstName() + " " + user.lastName() + " is " + state;
    }

    /**
     * Determines the descriptive age category based on the numerical age.
     * @param age The age in years.
     * @return A string representation of the age group (kid, minor, young adult, or adult).
     */
    public String determineState(int age) {
        if (age < 10) return "a kid";
        if (age < 18) return "a minor";
        if (age < 21) return "a young adult";
        return "an adult";
    }
}