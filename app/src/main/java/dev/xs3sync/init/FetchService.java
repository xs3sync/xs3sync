package dev.xs3sync.init;

import dev.xs3sync.project.Project;
import jakarta.annotation.Nonnull;

public class FetchService {

    public void fetch(final @Nonnull Project project) {
        final String path = project.getPath();
    }
}