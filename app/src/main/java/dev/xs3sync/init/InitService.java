package dev.xs3sync.init;

import dev.xs3sync.FilesUtil;
import dev.xs3sync.project.ProjectRepository;
import dev.xs3sync.storage.StorageUtil;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.nio.file.Path;

public class InitService {
    private final @Nonnull ProjectRepository projectRepository;
    private final @Nonnull FilesUtil filesUtil;
    private final @Nonnull StorageUtil storageUtil;

    public InitService(
        final @Nonnull ProjectRepository projectRepository,
        final @Nonnull FilesUtil filesUtil,
        final @Nonnull StorageUtil storageUtil
    ) {
        this.projectRepository = projectRepository;
        this.filesUtil = filesUtil;
        this.storageUtil = storageUtil;
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
        if (!filesUtil.isDirectory(Path.of(workingDirectory))) {
            throw new RuntimeException("Working directory %s does not exist or is not a directory".formatted(workingDirectory));
        }

        if (!filesUtil.isEmpty(Path.of(workingDirectory))) {
            throw new RuntimeException("Working directory %s is not empty".formatted(workingDirectory));
        }

        if (projectRepository.exists()) {
            throw new RuntimeException("Project already exists");
        }

        if (profile == null) {
            throw new RuntimeException("Profile is null");
        }

        projectRepository.create(bucket, region, profile);
    }
}
