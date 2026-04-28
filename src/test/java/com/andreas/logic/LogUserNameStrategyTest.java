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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LogUserNameStrategyTest {
    @Mock
    private MessageEmitter emitterMock;

    private LogUserNameStrategy strategy;

    @BeforeEach
    void setUp() {
        this.strategy = new LogUserNameStrategy(emitterMock);
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

        strategy.process(user);
        verify(emitterMock).emit("LOG: processing user " + firstName + " " + lastName);
    }
}
