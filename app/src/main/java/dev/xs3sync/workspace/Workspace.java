package dev.xs3sync.workspace;

import dev.xs3sync.PathUtil;
import jakarta.annotation.Nonnull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Workspace {
    private final List<Project> projects = new ArrayList<>();

    public Workspace(
        final @Nonnull String path,
        final @Nonnull PathUtil pathUtil
    ) {
        final Path path1 = pathUtil.expand(Path.of(path));
        final Path workspace = path1.resolve("workspace.yml");

        if (!Files.exists(workspace)) {
            // Utwórz plik workspace.yml
        }

        System.out.printf("test");
        // Path path = path.toPath();
        // System.out.printf("test");
    }
}