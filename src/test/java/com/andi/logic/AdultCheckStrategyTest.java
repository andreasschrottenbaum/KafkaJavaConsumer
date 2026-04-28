package com.andi.logic;

import com.andi.util.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdultCheckStrategyTest {
    private AdultCheckStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new AdultCheckStrategy();
    }

    @ParameterizedTest
    @DisplayName("Should correctly categorize user based on age")
    @CsvSource({
        "5,  a kid",
        "15, a minor",
        "19, a young adult",
        "30, an adult"
    })
    void testAgeCategorization(int yearsToSubtract, String expectedState) {
        var birthDate = LocalDate.now().minusYears(yearsToSubtract);

        var user = UserBuilder
                .anyUser()
                .withBirthDate(birthDate)
                .build();

        var result = strategy.formatOutput(user);

        assertTrue(result.contains(expectedState),
                "Expected  output to contain '" + expectedState + "' for age based on -" + yearsToSubtract + " years");
    }

    @Test
    @DisplayName("Full string format check")
    void testFullStringFormat() {
        var birthDate = LocalDate.now().minusYears(43);

        var user = UserBuilder
                .anyUser()
                .withBirthDate(birthDate)
                .withName("Andreas", "Schrottenbaum")
                .build();

        var result = strategy.formatOutput(user);

        assertEquals("User Andreas Schrottenbaum is an adult", result);
    }
}
