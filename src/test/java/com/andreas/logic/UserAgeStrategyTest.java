package com.andreas.logic;

import com.andreas.util.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAgeStrategyTest {
    private UserAgeStrategy strategy;

    @BeforeEach
    void setUp() {
        this.strategy = new UserAgeStrategy();
    }

    @ParameterizedTest
    @DisplayName("Should return the age of the user")
    @CsvSource({
        "1,   1",
        "50,  50",
        "100, 100"
    })
    void verifyAge(int age, int expected) {
        var birthDate = LocalDate.now().minusYears(age);
        var user = UserBuilder
                .anyUser()
                .withBirthDate(birthDate)
                .build();

        var result = strategy.getAge(user);
        assertEquals(expected, result);

        var birthdayTomorrow = birthDate.plusDays(1);
        var user1 = UserBuilder
                .anyUser()
                .withBirthDate(birthdayTomorrow)
                .build();

        var resultTomorrow = strategy.getAge(user1);
        assertEquals(expected - 1, resultTomorrow);
    }
}
