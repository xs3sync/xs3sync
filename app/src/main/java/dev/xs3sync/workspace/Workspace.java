package dev.xs3sync.workspace;

import dev.xs3sync.FileUtil;
import dev.xs3sync.PathUtil;
import dev.xs3sync.YamlMapper;
import jakarta.annotation.Nonnull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Workspace {
    private final List<Project> projects = new ArrayList<>();

    public Workspace(
        final @Nonnull String path,
        final @Nonnull PathUtil pathUtil,
        final @Nonnull FileUtil fileUtil,
        final @Nonnull YamlMapper yamlMapper
    ) {
        final Path path1 = pathUtil.expand(Path.of(path));
        final Path workspacePath = path1.resolve("workspace.yml");
        final Path projectTemplatePath = path1.resolve("project-template.yml");
        final Path projectsPath = path1.resolve("projects");

        if (!fileUtil.exists(workspacePath)) {
            fileUtil.copy(fileUtil.getInputStreamFromResource("workspace-template.yml"), workspacePath);
        }

        if (!fileUtil.exists(projectTemplatePath)) {
            fileUtil.copy(fileUtil.getInputStreamFromResource("project-template.yml"), projectTemplatePath);
        }

        if (!fileUtil.isDirectory(projectsPath)) {
            fileUtil.createDirectories(projectsPath);
        }

        for (final Path projectPath : fileUtil.list(projectsPath).toList()) {
            final Project project = loadProject(projectPath, yamlMapper);
        }

        // final List<Path> projects = fileUtil.list(path1).toList();

        System.out.printf("test");
        // Path path = path.toPath();
        // System.out.printf("test");
    }

    private @Nonnull Project loadProject(
        final @Nonnull Path projectPath,
        final @Nonnull YamlMapper yamlMapper
    ) {
        final ProjectYml projectYml = yamlMapper.readValue(projectPath.toFile(), ProjectYml.class);
    }
}