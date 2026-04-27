package com.andi.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record User (
    UUID id,
    String firstName,
    String lastName,
    LocalDate birthDate,
    int trustLevel,
    Instant lastSeen
){}