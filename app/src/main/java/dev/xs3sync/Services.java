package dev.xs3sync;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import dev.xs3sync.configuration.Configuration;

public class Services {
    private static @Nullable Configuration configuration = null;

    public synchronized static void setConfiguration(final @Nonnull Configuration configuration) {
        Services.configuration = configuration;
    }

    public synchronized static @Nonnull Configuration configuration() {
        if (configuration == null) {
            throw new IllegalStateException("Configuration not initialized yet.");
        }

        return configuration;
    }
}
