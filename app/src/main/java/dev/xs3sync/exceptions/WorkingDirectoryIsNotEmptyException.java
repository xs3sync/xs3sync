package dev.xs3sync.exceptions;

import jakarta.annotation.Nonnull;

public class WorkingDirectoryIsNotEmptyException extends RuntimeException {
    public WorkingDirectoryIsNotEmptyException(
        final @Nonnull String workingDirectory
    ) {
        super("Working directory %s is not empty".formatted(workingDirectory));
    }
}