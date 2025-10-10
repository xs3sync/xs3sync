package dev.xs3sync.sync;

import dev.xs3sync.Bucket;
import dev.xs3sync.FilesUtil;
import dev.xs3sync.storage.Storage;
import dev.xs3sync.storage.StorageItem;
import dev.xs3sync.storage.StorageItemState;
import dev.xs3sync.storage.StorageUtil;
import jakarta.annotation.Nonnull;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.nio.file.Path;
import java.util.List;

public class SyncService {
    private final @Nonnull Path path;
    private final @Nonnull FilesUtil filesUtil;
    private final @Nonnull Bucket bucket;
    private final @Nonnull StorageUtil storageUtil;

    public SyncService(
        final @Nonnull Path path,
        final @Nonnull FilesUtil filesUtil,
        final @Nonnull Bucket bucket,
        final @Nonnull StorageUtil storageUtil
    ) {
        this.path = path;
        this.filesUtil = filesUtil;
        this.bucket = bucket;
        this.storageUtil = storageUtil;
    }

    public void sync() {
        // local storage -----------------------------------------------------------------------------------------------
        final Storage localStorage = new Storage();
        final List<Path> files = filesUtil.walk(path, List.of(), List.of())
            .toList();

        for (final Path file : files) {
            localStorage.addItem(
                path.relativize(file).toString(),
                filesUtil.getLastModifiedTime(file),
                StorageItemState.synced
            );
        }

        // remote storage ----------------------------------------------------------------------------------------------
        final Storage remoteStorage = new Storage();
        final List<S3Object> objects = bucket.listObjects();

        for (final S3Object object : objects) {
            final StorageUtil.DecodedKey decodedKey = storageUtil.decodeKey(object.key());

            remoteStorage.addItem(
                decodedKey.path(),
                decodedKey.modifiedAt(),
                StorageItemState.valueOf(decodedKey.state())
            );
        }

        // sync --------------------------------------------------------------------------------------------------------
        for (final StorageItem localItem : localStorage.getItems()) {
            final Path localItemPath = path.resolve(localItem.path());
            final StorageItem remoteItem = remoteStorage.getItem(localItem.path());

            if (remoteItem == null) {
                bucket.putObject(
                    getStorageItemKey(localItem),
                    filesUtil.getInputStream(localItemPath),
                    filesUtil.getSize(localItemPath)
                );
            } else {
                if (localItem.modificationAt() > remoteItem.modificationAt()) {
                    bucket.putObject(
                        getStorageItemKey(localItem),
                        filesUtil.getInputStream(localItemPath),
                        filesUtil.getSize(localItemPath)
                    );
                } else if (localItem.modificationAt() < remoteItem.modificationAt()) {
                    if (StorageItemState.deleted.equals(remoteItem.state())) {
                        filesUtil.delete(localItemPath);
                    } else if (StorageItemState.synced.equals(remoteItem.state())) {
                        filesUtil.copy(
                            bucket.getObject(getStorageItemKey(remoteItem)),
                            localItemPath
                        );

                        filesUtil.setLastModifiedTime(localItemPath, remoteItem.modificationAt());
                    }
                }
            }
        }

        for (final StorageItem remoteItem : remoteStorage.getItems()) {
            final Path localItemPath = path.resolve(remoteItem.path());
            final StorageItem localItem = localStorage.getItem(remoteItem.path());

            if (localItem == null) {
                if (StorageItemState.synced.equals(remoteItem.state())) {
                    bucket.move(
                        getStorageItemKey(remoteItem),
                        getStorageItemKey(remoteItem, StorageItemState.deleted)
                    );
                }
            }
        }
    }

    private String getStorageItemKey(final @Nonnull StorageItem item) {
        return String.format("%s.%d.%s", item.path(), item.modificationAt(), item.state().name());
    }

    private String getStorageItemKey(final @Nonnull StorageItem item, final @Nonnull StorageItemState state) {
        return String.format("%s.%d.%s", item.path(), item.modificationAt(), state.name());
    }
}