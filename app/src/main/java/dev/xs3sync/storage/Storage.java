package dev.xs3sync.storage;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage {
    private final Map<String, List<StorageItem>> items = new HashMap<>();

    public void addItem(
        final @Nonnull String path,
        final @Nonnull Long modificationAt,
        final @Nonnull StorageItemState state
    ) {
        final List<StorageItem> versions = items.computeIfAbsent(path, k -> new ArrayList<>());
        versions.add(new StorageItem(path, modificationAt, state));
        versions.sort((o1, o2) -> Long.compare(o1.modificationAt(), o2.modificationAt()) * -1);
    }

    public @Nonnull List<StorageItem> getItems() {
        final List<StorageItem> result = new ArrayList<>();
        for (final List<StorageItem> versions : items.values()) {
            result.add(versions.getFirst());
        }

        return result;
    }

    public @Nullable StorageItem getItem(final @Nonnull String path) {
        final @Nullable List<StorageItem> result = this.items.get(path);

        if (result == null) {
            return null;
        } else {
            return result.getFirst();
        }
    }

    public boolean existsItem(
        final @Nonnull String path,
        final @Nonnull Long modificationAt
    ) {
        final @Nullable List<StorageItem> result = this.items.get(path);

        if (result == null) {
            return false;
        } else {
            return result.stream()
                .anyMatch(o -> o.modificationAt().equals(modificationAt));
        }
    }

    public @Nullable StorageItem getItem(
        final @Nonnull String path,
        final @Nonnull Long modificationAt
    ) {
        final @Nullable List<StorageItem> result = this.items.get(path);

        if (result == null) {
            return null;
        } else {
            return result.stream()
                .filter(o -> o.modificationAt().equals(modificationAt))
                .findFirst()
                .orElse(null);
        }
    }
}
