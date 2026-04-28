package com.andreas.logic;

import com.andreas.domain.MessageEmitter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PlaintextStrategyTest {
    @Mock
    private MessageEmitter emitterMock;

    private PlaintextStrategy strategy;

    @BeforeEach
    void setUp() {
        this.strategy = new PlaintextStrategy(emitterMock);
    }

    @ParameterizedTest
    @DisplayName("Should log the input 1:1")
    @CsvSource({
        "This was a triumph",
        "I'm making a note here",
        "Huge Success"
    })
    void validatePlaintextStrategy(String message) {
        strategy.process(message);

        verify(emitterMock).emit(message);
    }
}
