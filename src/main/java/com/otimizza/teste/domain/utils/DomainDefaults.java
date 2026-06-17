package com.otimizza.teste.domain.utils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Utility class to provide default values for domain entities.
 */
public class DomainDefaults {

    /**
     * Returns the provided ID or generates a new UUID if null.
     */
    public static String id(String id) {
        return Objects.requireNonNullElseGet(id, () -> UUID.randomUUID().toString());
    }

    /**
     * Returns the provided createdAt date or the current time in UTC if null.
     */
    public static OffsetDateTime createdAt(OffsetDateTime createdAt) {
        return Objects.requireNonNullElseGet(createdAt, () -> OffsetDateTime.now(ZoneOffset.UTC));
    }

    /**
     * Returns an immutable copy of the provided tags list or an empty list if null.
     */
    public static <T> List<T> tags(List<T> tags) {
        return List.copyOf(Objects.requireNonNullElse(tags, List.of()));
    }
}
