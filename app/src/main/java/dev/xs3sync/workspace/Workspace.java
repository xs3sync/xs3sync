package dev.xs3sync.workspace;

import dev.xs3sync.FilesUtil;
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
        final @Nonnull FilesUtil fileUtil,
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
            projects.add(loadProject(projectPath, yamlMapper, fileUtil, pathUtil));
        }
    }

    public @Nonnull List<Project> getProjects() {
        return projects;
    }

    private @Nonnull Project loadProject(
        final @Nonnull Path projectPath,
        final @Nonnull YamlMapper yamlMapper,
        final @Nonnull FilesUtil fileUtil,
        final @Nonnull PathUtil pathUtil
    ) {
        final ProjectYml projectYml = yamlMapper.readValue(projectPath.toFile(), ProjectYml.class);
        final Project.Builder builder = Project.builder();

        builder.id(fileUtil.getFileName(projectPath, true).toString());

        if (projectYml.source() != null)
            builder.source(pathUtil.expand(Path.of(projectYml.source())).toString());

        if (projectYml.destination() != null) {
            if (projectYml.destination().bucket() != null)
                builder.destinationBucket(projectYml.destination().bucket());

            if (projectYml.destination().region() != null)
                builder.destinationRegion(projectYml.destination().region());

            if (projectYml.destination().accessKeyId() != null)
                builder.destinationAccessKeyId(projectYml.destination().accessKeyId());

            if (projectYml.destination().secretAccessKey() != null)
                builder.destinationSecretAccessKey(projectYml.destination().secretAccessKey());

            if (projectYml.destination().endpoint() != null)
                builder.destinationEndpoint(projectYml.destination().endpoint());
        }

        if (projectYml.include() != null)
            builder.include(projectYml.include());

        if (projectYml.exclude() != null)
            builder.exclude(projectYml.exclude());

        return builder.build();
    }
}