package com.andi.logic;

import com.andi.util.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HighTrustUserStrategyTest {
    private HighTrustUserStrategy strategy;

    @BeforeEach
    void setUp() {
        this.strategy = new HighTrustUserStrategy();
    }

    @ParameterizedTest
    @DisplayName("Should only allow Users with trust level higher than 8")
    @CsvSource({
        "7, false",
        "8, false",
        "9, true"
    })
    void testTrusted(int level, boolean expected) {
        var user = UserBuilder
                .anyUser()
                .withTrustLevel(level)
                .build();

        var result = strategy.isTrusted(user);

        assertEquals(expected, result);
    }
}
