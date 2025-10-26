package dev.xs3sync.exceptions;

import jakarta.annotation.Nonnull;

public class ProjectAlreadyExistsExistsException extends RuntimeException {
    public ProjectAlreadyExistsExistsException(
        final @Nonnull String workingDirectory
    ) {
        super("Project %s already exists".formatted(workingDirectory));
    }
}