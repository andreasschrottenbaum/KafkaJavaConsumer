package com.andreas.logic;

import com.andreas.domain.MessageEmitter;
import com.andreas.util.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AdultCheckStrategyTest {
    @Mock
    private MessageEmitter emitterMock;

    private AdultCheckStrategy strategy;
    private Clock clock;

    @BeforeEach
    void setUp() {
        var fixedDate = LocalDate.of(2026, 4, 28);
        this.clock = Clock.fixed(fixedDate.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

        strategy = new AdultCheckStrategy(emitterMock, clock);
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
        var result = strategy.determineState(yearsToSubtract);

        assertEquals(expectedState, result);
    }

    @Test
    @DisplayName("Full string format check")
    void testFullStringFormat() {
        var birthDate = LocalDate.now(clock).minusYears(43);

        var user = UserBuilder
                .anyUser()
                .withBirthDate(birthDate)
                .withName("Andreas", "Schrottenbaum")
                .build();

        strategy.process(user);

        verify(emitterMock).emit("User Andreas Schrottenbaum is an adult");
    }
}
