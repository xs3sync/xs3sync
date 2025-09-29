package dev.xs3sync.storage;

import jakarta.annotation.Nonnull;

public record StorageItem(
    @Nonnull String key,
    @Nonnull String path,
    @Nonnull Long version,
    @Nonnull StorageItemState state,
    @Nonnull Long modificationAt
) {

}