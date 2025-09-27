package dev.xs3sync;

import jakarta.annotation.Nonnull;

import java.nio.file.Path;

public class SyncService {

    private final @Nonnull Path path;
    private final @Nonnull FilesUtil filesUtil;

    public SyncService(
        final @Nonnull Path path,
        final @Nonnull FilesUtil filesUtil
    ) {
        this.path = path;
        this.filesUtil = filesUtil;
    }

    public void sync() {
        // final List<Path> list = filesUtil.walk(path, List.of(), List.of())
        //     .toList();

        // System.out.printf("test");
    }
}
