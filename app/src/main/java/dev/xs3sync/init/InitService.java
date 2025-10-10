package dev.xs3sync.init;

import dev.xs3sync.FilesUtil;
import dev.xs3sync.PathUtil;
import dev.xs3sync.YamlMapper;
import dev.xs3sync.project.Project;
import dev.xs3sync.project.ProjectRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class InitService {
    final @Nonnull PathUtil pathUtil;
    final @Nonnull FilesUtil fileUtil;
    final @Nonnull YamlMapper yamlMapper;
    final @Nonnull ProjectRepository projectRepository;
    final @Nonnull FetchService fetchService;

    public InitService(
        final @Nonnull PathUtil pathUtil,
        final @Nonnull FilesUtil fileUtil,
        final @Nonnull YamlMapper yamlMapper,
        final @Nonnull ProjectRepository projectRepository,
        final @Nonnull FetchService fetchService
    ) {
        this.pathUtil = pathUtil;
        this.fileUtil = fileUtil;
        this.yamlMapper = yamlMapper;
        this.projectRepository = projectRepository;
        this.fetchService = fetchService;
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
        if (projectRepository.exists()) {
            throw new RuntimeException("Project already exists");
        }

        final Project.Builder builder = Project.builder(workingDirectory);

        if (profile != null) {
            builder.setDestination(bucket, region, profile);
        } else {
            // throw new RuntimeException("Either profile or access keys must be provided");
        }

        final Project project = builder.build();

        projectRepository.create(project);
        fetchService.fetch(project);
    }
}
