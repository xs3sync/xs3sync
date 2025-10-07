package dev.xs3sync.workspace;

import dev.xs3sync.FilesUtil;
import dev.xs3sync.PathUtil;
import dev.xs3sync.YamlMapper;
import dev.xs3sync.project.Project;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

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
        final WorkspaceProjectYml workspaceProjectYml = yamlMapper.readValue(projectPath.toFile(), WorkspaceProjectYml.class);

        System.out.printf("test");
        // final Project.Builder builder = Project.builder();

        // builder.id(fileUtil.getFileName(projectPath, true).toString());

        // @Nullable String source = null;

        // if (workspaceProjectYml.source() != null) {
        //     source = pathUtil.expand(Path.of(workspaceProjectYml.source())).toString();
        //     builder.source(source);
        // }

        // if (workspaceProjectYml.destination() != null) {
        //     if (workspaceProjectYml.destination().bucket() != null)
        //         builder.destinationBucket(workspaceProjectYml.destination().bucket());

        //     if (workspaceProjectYml.destination().region() != null)
        //         builder.destinationRegion(workspaceProjectYml.destination().region());

        //     if (workspaceProjectYml.destination().accessKeyId() != null)
        //         builder.destinationAccessKeyId(workspaceProjectYml.destination().accessKeyId());

        //     if (workspaceProjectYml.destination().secretAccessKey() != null)
        //         builder.destinationSecretAccessKey(workspaceProjectYml.destination().secretAccessKey());

        //     if (workspaceProjectYml.destination().profile() != null)
        //         builder.destinationProfile(workspaceProjectYml.destination().profile());

        //     if (workspaceProjectYml.destination().endpoint() != null)
        //         builder.destinationEndpoint(workspaceProjectYml.destination().endpoint());
        // }

        // if (workspaceProjectYml.include() != null)
        //     builder.include(workspaceProjectYml.include());

        // if (workspaceProjectYml.exclude() != null)
        //     builder.exclude(workspaceProjectYml.exclude());

        // if (source != null) {
        //     final Path sourceXs3sync = Path.of(source, ".xs3sync");

        //     if (!fileUtil.exists(sourceXs3sync)) {
        //         fileUtil.createDirectories(sourceXs3sync);
        //     }
        // }

        // return builder.build();
        return null;
    }
}