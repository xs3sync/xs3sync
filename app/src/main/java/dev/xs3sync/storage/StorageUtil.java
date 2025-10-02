package dev.xs3sync.storage;

import jakarta.annotation.Nonnull;

public class StorageUtil {

    public @Nonnull DecodedKey decodeKey(final @Nonnull String key) {
        if (key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty");
        }

        int lastDot = key.lastIndexOf('.');

        if (lastDot == -1) {
            throw new IllegalArgumentException("Invalid key format: " + key);
        }

        final String state = key.substring(lastDot + 1);

        int secondLastDot = key.lastIndexOf('.', lastDot - 1);
        if (secondLastDot == -1) {
            throw new IllegalArgumentException("Invalid key format: " + key);
        }

        final String modifiedAtStr = key.substring(secondLastDot + 1, lastDot);
        long modifiedAt;

        try {
            modifiedAt = Long.parseLong(modifiedAtStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid modifiedAt timestamp: " + modifiedAtStr, e);
        }

        String path = key.substring(0, secondLastDot);

        return new DecodedKey(path, modifiedAt, state);
    }

    public record DecodedKey(
        @Nonnull String path,
        @Nonnull Long modifiedAt,
        @Nonnull String state

    ) {

    }
}
