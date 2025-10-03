package dev.xs3sync.project;

import jakarta.annotation.Nonnull;

import java.time.LocalDateTime;

public class ProjectXs3sync {
    private final @Nonnull LocalDateTime syncedAt;

    public ProjectXs3sync(final @Nonnull LocalDateTime syncedAt) {
        this.syncedAt = syncedAt;
    }
}
