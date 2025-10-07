package dev.xs3sync.init;

import dev.xs3sync.FilesUtil;
import dev.xs3sync.PathUtil;
import dev.xs3sync.YamlMapper;
import dev.xs3sync.project.ProjectConfigYml;
import jakarta.annotation.Nonnull;

import java.nio.file.Path;
import java.nio.file.Paths;

public class InitService {
    final @Nonnull PathUtil pathUtil;
    final @Nonnull FilesUtil fileUtil;
    final @Nonnull YamlMapper yamlMappe;

    public InitService(
        final @Nonnull PathUtil pathUtil,
        final @Nonnull FilesUtil fileUtil,
        final @Nonnull YamlMapper yamlMappe
    ) {
        this.pathUtil = pathUtil;
        this.fileUtil = fileUtil;
        this.yamlMappe = yamlMappe;
    }

    public void init(final @Nonnull String workingDirectory) {
        final Path xs3syncPath = Paths.get(workingDirectory).resolve(".xs3sync");

        if (fileUtil.exists(xs3syncPath)) {
            throw new RuntimeException("The xs3sync repository already exists at: " + xs3syncPath);
        }

        fileUtil.createDirectories(xs3syncPath);

        // creating project directory ----------------------------------------------------------------------------------
        final Path configPath = xs3syncPath.resolve(xs3syncPath.resolve("config.yaml"));

        final ProjectConfigYml configYml = new ProjectConfigYml(
            new ProjectConfigYml.SourceYml("xs3sync project configuration file"),
            new ProjectConfigYml.DestinationYml(
                "handy-1c6966d6-9bb4-11f0-8de9-0242ac120002",
                "eu-central-1",
                "key",
                "secret",
                "pawel-bobryk",
                "endpoint"
            )
        );

        yamlMappe.write(configYml, configPath.toString());
    }
}
