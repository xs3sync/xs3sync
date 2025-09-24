package dev.xs3sync.workspace;

import jakarta.annotation.Nonnull;

public class Project
{
    private @Nonnull String id;

    public Project(@Nonnull final String id)
    {
        this.id = id;
    }
}
