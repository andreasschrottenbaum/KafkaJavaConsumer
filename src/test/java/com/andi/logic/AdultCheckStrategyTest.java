package com.andi.logic;

import com.andi.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

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
        var user = createTestUser(yearsToSubtract);
        var result = strategy.formatOutput(user);

        assertTrue(result.contains(expectedState),
                "Expected  output to contain '" + expectedState + "' for age based on -" + yearsToSubtract + " years");
    }

    @Test
    @DisplayName("Full string format check")
    void testFullStringFormat() {
        var user = new User(
            new UUID(100, 10),
            "Andreas",
            "Schrottenbaum",
            LocalDate.now().minusYears(43),
            10,
            Instant.now()
        );
        var result = strategy.formatOutput(user);

        assertEquals("User Andreas Schrottenbaum is an adult", result);
    }

    private User createTestUser(int age) {
        return new User(
            new UUID(100, 10),
            "firstName",
            "lastName",
            LocalDate.now().minusYears(age),
            5,
            Instant.now()
        );
    }
}
