package dev.xs3sync.fetch;

import dev.xs3sync.Bucket;
import dev.xs3sync.FilesUtil;
import dev.xs3sync.project.Project;
import dev.xs3sync.project.ProjectRepository;
import dev.xs3sync.storage.Storage;
import dev.xs3sync.storage.StorageItem;
import dev.xs3sync.storage.StorageItemState;
import dev.xs3sync.storage.StorageUtil;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.nio.file.Path;
import java.util.List;

public class FetchService {
    private final @Nonnull ProjectRepository projectRepository;
    private final @Nonnull FilesUtil filesUtil;
    private final @Nonnull StorageUtil storageUtil;

    public FetchService(
        final @Nonnull ProjectRepository projectRepository,
        final @Nonnull FilesUtil filesUtil,
        final @Nonnull StorageUtil storageUtil
    ) {
        this.projectRepository = projectRepository;
        this.filesUtil = filesUtil;
        this.storageUtil = storageUtil;
    }

    public void fetch(
        final @Nonnull String workingDirectory,
        final @Nonnull String bucket,
        final @Nonnull String region,
        final @Nullable String accessKeyId,
        final @Nullable String secretAccessKey,
        final @Nullable String profile,
        final @Nullable String endpoint
    ) {
        if (profile == null) {
            throw new IllegalArgumentException("profile is null");
        }

        final Project project = init(workingDirectory, bucket, region, accessKeyId, secretAccessKey, profile, endpoint);
        final Path path = Path.of(project.getPath());
        final Bucket bucket1 = Bucket.createWithProfileCredentials(bucket, region, profile);

        // fetching ----------------------------------------------------------------------------------------------------
        final Storage remoteStorage = new Storage();
        final List<S3Object> objects = bucket1.listObjects();

        for (final S3Object object : objects) {
            final StorageUtil.DecodedKey decodedKey = storageUtil.decodeKey(object.key());

            remoteStorage.addItem(
                decodedKey.path(),
                decodedKey.modifiedAt(),
                StorageItemState.valueOf(decodedKey.state())
            );
        }

        for (final StorageItem remoteItem : remoteStorage.getItems()) {
            final Path localItemPath = path.resolve(remoteItem.path());
            if (StorageItemState.synced.equals(remoteItem.state())) {
                filesUtil.copy(
                    bucket1.getObject(storageUtil.getStorageItemKey(remoteItem)),
                    localItemPath
                );

                filesUtil.setLastModifiedTime(localItemPath, remoteItem.modificationAt());
            }
        }
    }

    private @Nonnull Project init(
        final @Nonnull String workingDirectory,
        final @Nonnull String bucket,
        final @Nonnull String region,
        final @Nullable String accessKeyId,
        final @Nullable String secretAccessKey,
        final @Nullable String profile,
        final @Nullable String endpoint
    ) {
        // todo
        // if (!filesUtil.isDirectory(Path.of(workingDirectory))) {
        //     throw new RuntimeException("Working directory %s does not exist or is not a directory".formatted(workingDirectory));
        // }

        // if (!filesUtil.isEmpty(Path.of(workingDirectory))) {
        //     throw new RuntimeException("Working directory %s is not empty".formatted(workingDirectory));
        // }

        // if (projectRepository.exists()) {
        //     throw new RuntimeException("Project already exists");
        // }

        // final Project.Builder builder = Project.builder(workingDirectory);

        // if (profile != null) {
        //     builder.setDestination(bucket, region, profile);
        // } else {
        //     // throw new RuntimeException("Either profile or access keys must be provided");
        // }

        // final Project project = builder.build();
        // projectRepository.create(project);
        // return project;
        return null;
    }
}