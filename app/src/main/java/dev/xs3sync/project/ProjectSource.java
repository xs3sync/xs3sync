package dev.xs3sync.project;

import jakarta.annotation.Nonnull;

public class ProjectSource {
    private final @Nonnull String path;

    public ProjectSource(final @Nonnull String path) {
        this.path = path;
    }

    public @Nonnull String getPath() {
        return path;
    }
}
