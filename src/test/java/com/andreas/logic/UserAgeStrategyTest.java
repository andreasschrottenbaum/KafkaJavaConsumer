package com.andreas.logic;

import com.andreas.domain.MessageEmitter;
import com.andreas.util.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
public class UserAgeStrategyTest {
    @Mock
    private MessageEmitter emitterMock;

    private UserAgeStrategy strategy;

    @BeforeEach
    void setUp() {
        var fixedDate = LocalDate.of(2026, 4, 28);
        var clock = Clock.fixed(fixedDate.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

        this.strategy = new UserAgeStrategy(emitterMock, clock);
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

    @ParameterizedTest
    @DisplayName("Should output the age and the birth date")
    @CsvSource({
        "1982-07-06, 43",
        "2000-01-01, 26",
        "1970-01-01, 56",
        "2026-04-28, 0"
    })
    void testFullString(String birthDate, int expectedAge) {
        var user = UserBuilder
                .anyUser()
                .withBirthDate(LocalDate.parse(birthDate))
                .build();

        strategy.process(user);

        verify(emitterMock).emit("Age: " + expectedAge + " ("+ birthDate +")");
    }
}
