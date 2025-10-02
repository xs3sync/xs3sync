package dev.xs3sync.storage;

import jakarta.annotation.Nonnull;

public record StorageItem(
    @Nonnull String path,
    @Nonnull Long modificationAt,
    @Nonnull StorageItemState state
) {
}