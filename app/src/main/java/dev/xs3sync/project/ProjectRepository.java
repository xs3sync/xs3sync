package dev.xs3sync.project;

import dev.xs3sync.FilesUtil;
import dev.xs3sync.PathUtil;
import dev.xs3sync.YamlMapper;
import dev.xs3sync.project.ProjectYamlV1.DestinationYml;
import jakarta.annotation.Nonnull;

import java.nio.file.Path;
import java.util.ArrayList;

public class ProjectRepository {

    private final @Nonnull String workingDirectory;
    private final @Nonnull PathUtil pathUtil;
    private final @Nonnull FilesUtil fileUtil;
    private final @Nonnull YamlMapper yamlMapper;

    public ProjectRepository(
        final @Nonnull String workingDirectory,
        final @Nonnull PathUtil pathUtil,
        final @Nonnull FilesUtil fileUtil,
        final @Nonnull YamlMapper yamlMapper
    ) {
        if (!Path.of(workingDirectory).isAbsolute()) {
            throw new RuntimeException("The working directory %s must be a relative path.".formatted(workingDirectory));
        }

        this.workingDirectory = workingDirectory;
        this.pathUtil = pathUtil;
        this.fileUtil = fileUtil;
        this.yamlMapper = yamlMapper;
    }

    public boolean exists() {
        final Path xs3syncPath = Path.of(workingDirectory).resolve(".xs3sync");
        return fileUtil.exists(xs3syncPath);
    }

    public @Nonnull Project get() {
        final Path xs3syncPath = Path.of(workingDirectory).resolve(".xs3sync");

        if (!fileUtil.exists(xs3syncPath)) {
            throw new RuntimeException("The directory %s is not a xs3sync project.".formatted(workingDirectory));
        }

        final Path projectYamlPath = xs3syncPath.resolve("project.yml");
        final ProjectYamlV1 projectYaml = yamlMapper.readValue(projectYamlPath.toFile(), ProjectYamlV1.class);

        return new Project(
            projectYaml.version(),
            workingDirectory,
            projectYaml.fetched(),
            new Project.Destination(
                projectYaml.destination().bucket(),
                projectYaml.destination().region(),
                projectYaml.destination().accessKeyId(),
                projectYaml.destination().secretAccessKey(),
                projectYaml.destination().profile(),
                projectYaml.destination().endpoint()
            ),
            new ArrayList<>(),
            new ArrayList<>()
        );
    }

    public void create(
        final @Nonnull String bucket,
        final @Nonnull String region,
        final @Nonnull String profile
    ) {
        final @Nonnull Project project = new Project(
            1,
            workingDirectory,
            false,
            new Project.Destination(
                bucket,
                region,
                null,
                null,
                profile,
                null
            ),
            new ArrayList<>(),
            new ArrayList<>()
        );

        create(project);
    }

    public void create(final @Nonnull Project project) {
        final Path xs3syncPath = Path.of(workingDirectory).resolve(".xs3sync");
        final Path projectYamlPath = xs3syncPath.resolve("project.yml");

        if (fileUtil.exists(xs3syncPath)) {
            throw new RuntimeException("The directory %s is already a xs3sync project.".formatted(workingDirectory));
        }

        final ProjectYamlV1 projectYaml = new ProjectYamlV1(
            1,
            project.fetched,
            new DestinationYml(
                project.destination.bucket,
                project.destination.region,
                project.destination.accessKeyId,
                project.destination.secretAccessKey,
                project.destination.profile,
                project.destination.endpoint
            )
        );

        fileUtil.createDirectories(xs3syncPath);
        yamlMapper.write(projectYaml, projectYamlPath.toString());
    }
}
