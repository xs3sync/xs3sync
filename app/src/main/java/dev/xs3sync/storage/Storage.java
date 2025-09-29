package dev.xs3sync.storage;

import jakarta.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final List<StorageItem> items = new ArrayList<>();

    public void addItem(
        final @Nonnull String key,
        final @Nonnull Long modificationAt
    ) {


    }
}
