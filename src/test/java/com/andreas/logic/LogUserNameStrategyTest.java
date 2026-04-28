package com.andreas.logic;

import com.andreas.util.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogUserNameStrategyTest {
    private LogUserNameStrategy strategy;

    @BeforeEach
    void setUp() {
        this.strategy = new LogUserNameStrategy();
    }

    @ParameterizedTest
    @DisplayName("Should combine firstName and lastName")
    @CsvSource({
        "Andreas, Schrottenbaum, Andreas Schrottenbaum",
        "Test,    User,          Test User",
        "👍,     👌,             👍 👌"
    })
    void validateUserName(String firstName, String lastName, String expectation)
    {
        var user = UserBuilder
                .anyUser()
                .withName(firstName, lastName)
                .build();

        var result = strategy.getName(user);

        assertEquals(expectation, result);
    }
}
