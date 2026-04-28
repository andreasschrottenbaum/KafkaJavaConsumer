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
import static org.mockito.Mockito.verifyNoInteractions;


@ExtendWith(MockitoExtension.class)
public class HighTrustUserStrategyTest {
    @Mock
    private MessageEmitter emitterMock;

    private HighTrustUserStrategy strategy;

    @BeforeEach
    void setUp() {
        this.strategy = new HighTrustUserStrategy(emitterMock);
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

    @ParameterizedTest
    @DisplayName("Should emit message for high trust users")
    @CsvSource({"9", "10", "100"})
    void shouldEmitWhenTrustIsHigh(int level) {
        var user = UserBuilder.anyUser().withTrustLevel(level).build();

        strategy.process(user);

        verify(emitterMock).emit("🌟 Trusted User: ID " + user.id() +" 🌟");
    }

    @ParameterizedTest
    @DisplayName("Should NOT emit anything for low trust users")
    @CsvSource({"0", "5", "8"})
    void shouldSilenceWhenTrustIsLow(int level) {
        var user = UserBuilder.anyUser().withTrustLevel(level).build();

        strategy.process(user);

        verifyNoInteractions(emitterMock);
    }
}
