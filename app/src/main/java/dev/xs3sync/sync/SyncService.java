package dev.xs3sync.sync;

import dev.xs3sync.Bucket;
import dev.xs3sync.FilesUtil;
import dev.xs3sync.project.Project;
import dev.xs3sync.project.Project.FileMetadata;
import dev.xs3sync.project.ProjectRepository;
import dev.xs3sync.storage.Storage;
import dev.xs3sync.storage.StorageItem;
import dev.xs3sync.storage.StorageItemState;
import dev.xs3sync.storage.StorageUtil;
import jakarta.annotation.Nonnull;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.nio.file.Path;
import java.util.List;

public class SyncService {
    private final @Nonnull FilesUtil filesUtil;
    private final @Nonnull StorageUtil storageUtil;
    private final @Nonnull ProjectRepository projectRepository;

    public SyncService(
        final @Nonnull FilesUtil filesUtil,
        final @Nonnull StorageUtil storageUtil,
        final @Nonnull ProjectRepository projectRepository
    ) {
        this.filesUtil = filesUtil;
        this.storageUtil = storageUtil;
        this.projectRepository = projectRepository;
    }

    public void sync(final @Nonnull Project project) {
        if (!project.getFetched()) {
            fetch(project);
            return;
        }

        final Path path = Path.of(project.getPath());
        final Bucket bucket = Bucket.createWithParameters(
            project.getDestination().getBucket(),
            project.getDestination().getRegion(),
            project.getDestination().getProfile(),
            project.getDestination().getAccessKeyId(),
            project.getDestination().getSecretAccessKey(),
            project.getDestination().getEndpoint()
        );

        final Storage localStorage = loadLocalStorage(project);
        final Storage remoteStorage = loadRemoteStorage(bucket);

        // sync --------------------------------------------------------------------------------------------------------
        for (final StorageItem localItem : localStorage.getItems()) {
            final Path localItemPath = path.resolve(localItem.path());
            final StorageItem remoteItem = remoteStorage.getItem(localItem.path());

            if (remoteItem == null) {
                bucket.putObject(
                    storageUtil.getStorageItemKey(localItem),
                    filesUtil.getInputStream(localItemPath),
                    filesUtil.getSize(localItemPath)
                );
            } else {
                if (localItem.modificationAt() > remoteItem.modificationAt()) {
                    bucket.putObject(
                        storageUtil.getStorageItemKey(localItem),
                        filesUtil.getInputStream(localItemPath),
                        filesUtil.getSize(localItemPath)
                    );
                } else if (localItem.modificationAt() < remoteItem.modificationAt()) {
                    if (StorageItemState.deleted.equals(remoteItem.state())) {
                        filesUtil.delete(localItemPath);
                    } else if (StorageItemState.synced.equals(remoteItem.state())) {
                        filesUtil.copy(
                            bucket.getObject(storageUtil.getStorageItemKey(remoteItem)),
                            localItemPath
                        );

                        filesUtil.setLastModifiedTime(localItemPath, remoteItem.modificationAt());
                    }
                }
            }
        }

        for (final FileMetadata file : project.getFiles()) {
            final StorageItem localItem = localStorage.getItem(file.getPath());

            if (localItem == null) {
                final StorageItem remoteItem = remoteStorage.getItem(file.getPath());
                if (remoteItem != null && !StorageItemState.deleted.equals(remoteItem.state())) {
                    bucket.move(
                        storageUtil.getStorageItemKey(remoteItem),
                        storageUtil.getStorageItemKey(remoteItem, StorageItemState.deleted)
                    );
                }
            }
        }

        updateProjectFiles(project);
    }

    private @Nonnull Storage loadRemoteStorage(Bucket bucket) {
        final Storage storage = new Storage();
        final List<S3Object> objects = bucket.listObjects();

        for (final S3Object object : objects) {
            final StorageUtil.DecodedKey decodedKey = storageUtil.decodeKey(object.key());

            storage.addItem(
                decodedKey.path(),
                decodedKey.modifiedAt(),
                StorageItemState.valueOf(decodedKey.state())
            );
        }
        return storage;
    }

    private Storage loadLocalStorage(final @Nonnull Project project) {
        final Storage storage = new Storage();
        final Path path = Path.of(project.getPath());
        final List<Path> files = filesUtil.walk(path, List.of(), List.of())
            .filter(path1 -> {
                return !path.relativize(path1).startsWith(".xs3sync");
            })
            .toList();

        for (final Path file : files) {
            storage.addItem(
                path.relativize(file).toString(),
                filesUtil.getLastModifiedTime(file),
                StorageItemState.synced
            );
        }

        return storage;
    }

    private void setProjectFetched(
        final Project project,
        final @Nonnull Boolean fetched
    ) {
        project.setFetched(fetched);
        projectRepository.save(project);
    }

    private void updateProjectFiles(
        final @Nonnull Project project
    ) {
        final Storage storage = loadLocalStorage(project);
        project.cleanFiles();

        for (final StorageItem item : storage.getItems()) {
            project.addFile(item.path(), item.modificationAt());
        }

        projectRepository.save(project);
    }

    private void fetch(final @Nonnull Project project) {
//        final String profile = project.getDestination().getProfile();
//        final Path path = Path.of(project.getPath());
//
//        if (profile == null) {
//            throw new DestinationProfileIsNotSetException();
//        }
//
//        final Bucket bucket = Bucket.createWithProfileCredentials(
//            project.getDestination().getBucket(),
//            project.getDestination().getRegion(),
//            profile
//        );
//
//        final Storage remoteStorage = new Storage();
//        final List<S3Object> objects = bucket.listObjects();
//
//        for (final S3Object object : objects) {
//            final StorageUtil.DecodedKey decodedKey = storageUtil.decodeKey(object.key());
//
//            remoteStorage.addItem(
//                decodedKey.path(),
//                decodedKey.modifiedAt(),
//                StorageItemState.valueOf(decodedKey.state())
//            );
//        }
//
//        for (final StorageItem remoteItem : remoteStorage.getItems()) {
//            final Path localItemPath = path.resolve(remoteItem.path());
//            if (StorageItemState.synced.equals(remoteItem.state())) {
//                filesUtil.copy(
//                    bucket.getObject(storageUtil.getStorageItemKey(remoteItem)),
//                    localItemPath
//                );
//
//                filesUtil.setLastModifiedTime(localItemPath, remoteItem.modificationAt());
//            }
//        }
//
        setProjectFetched(project, true);
    }
}