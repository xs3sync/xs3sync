package dev.xs3sync.init;

import dev.xs3sync.FilesUtil;
import dev.xs3sync.PathUtil;
import dev.xs3sync.YamlMapper;
import dev.xs3sync.project.Project;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.nio.file.Path;
import java.nio.file.Paths;

public class InitService {
    final @Nonnull PathUtil pathUtil;
    final @Nonnull FilesUtil fileUtil;
    final @Nonnull YamlMapper yamlMapper;

    public InitService(
        final @Nonnull PathUtil pathUtil,
        final @Nonnull FilesUtil fileUtil,
        final @Nonnull YamlMapper yamlMapper
    ) {
        this.pathUtil = pathUtil;
        this.fileUtil = fileUtil;
        this.yamlMapper = yamlMapper;
    }

    public void init(
        final @Nonnull String workingDirectory,
        final @Nonnull String bucket,
        final @Nonnull String region,
        final @Nullable String accessKeyId,
        final @Nullable String secretAccessKey,
        final @Nullable String profile,
        final @Nullable String endpoint
    ) {
        final Path xs3syncPath = Paths.get(workingDirectory).resolve(".xs3sync");

        if (fileUtil.exists(xs3syncPath)) {
            throw new RuntimeException("The xs3sync repository already exists at: " + xs3syncPath);
        }

        final Project.Builder builder = Project.builder();
        builder.setSourcePath(xs3syncPath.getParent().toString());

        if (profile != null) {
            builder.setDestination(bucket, region, profile);
        } else {
            // throw new RuntimeException("Either profile or access keys must be provided");
        }

        final Project project = builder.build();

        System.out.printf("test");
        // fileUtil.createDirectories(xs3syncPath);

        // // creating project directory ----------------------------------------------------------------------------------
        // final Path configPath = xs3syncPath.resolve(xs3syncPath.resolve("config.yaml"));

        // final ProjectConfigYml configYml = new ProjectConfigYml(
        //     new ProjectConfigYml.DestinationYml(
        //         bucket,
        //         region,
        //         accessKeyId,
        //         secretAccessKey,
        //         profile,
        //         endpoint
        //     )
        // );

        // yamlMapper.write(configYml, configPath.toString());
    }
}
