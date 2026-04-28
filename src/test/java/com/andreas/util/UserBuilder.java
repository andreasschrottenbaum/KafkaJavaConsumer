package com.andreas.util;

import com.andreas.domain.User;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class UserBuilder {
    private final UUID id = UUID.randomUUID();
    private String firstName = "Default";
    private String lastName =  "User";
    private LocalDate birthDate = LocalDate.now().minusYears(42);
    private int trustLevel = 1;
    private final Instant lastSeen = Instant.now();

    public static UserBuilder anyUser() {
        return new UserBuilder();
    }

    public User build() {
        return new User(id, firstName, lastName, birthDate, trustLevel, lastSeen);
    }

    public UserBuilder withBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;

        return this;
    }

    public UserBuilder withName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

        return this;
    }

    public UserBuilder withTrustLevel(int trustLevel) {
        this.trustLevel = trustLevel;

        return this;
    }
}
