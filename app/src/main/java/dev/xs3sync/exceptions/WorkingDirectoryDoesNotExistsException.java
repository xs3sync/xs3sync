package dev.xs3sync.exceptions;

import jakarta.annotation.Nonnull;

public class WorkingDirectoryDoesNotExistsException extends RuntimeException {
    public WorkingDirectoryDoesNotExistsException(
        final @Nonnull String workingDirectory
    ) {
        super("Working directory %s does not exist or is not a directory".formatted(workingDirectory));
    }
}