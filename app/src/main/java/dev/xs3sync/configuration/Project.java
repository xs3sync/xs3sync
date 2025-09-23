package dev.xs3sync.configuration;

import jakarta.annotation.Nonnull;

public class Project
{
    private @Nonnull String id;

    public Project(@Nonnull final String id)
    {
        this.id = id;
    }
}
