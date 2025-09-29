package dev.xs3sync;

import jakarta.annotation.Nonnull;

import java.nio.file.Path;
import java.util.List;

public class SyncService {
    private final @Nonnull Path path;
    private final @Nonnull FilesUtil filesUtil;
    private final @Nonnull Bucket bucket;

    public SyncService(
        final @Nonnull Path path,
        final @Nonnull FilesUtil filesUtil,
        final @Nonnull Bucket bucket
    ) {
        this.path = path;
        this.filesUtil = filesUtil;
        this.bucket = bucket;
    }

    public void sync() {
        final List<Path> files = filesUtil.walk(path, List.of(), List.of())
            .toList();

        for (final Path file : files) {
            final String key = path.relativize(file) + ".v1.synced";
            final long size = filesUtil.size(file);

            bucket.putObject(key, filesUtil.getInputStream(file), size);
        }

        System.out.println("files");
        // final List<S3Object> objects = bucket.listObjects();

        // for (final Path file : files) {
        //     final String key = path.relativize(file).toString();
        //     final long size = filesUtil.size(file);

        //     bucket.putObject(key, filesUtil.getInputStream(file), size);
        // }
    }
}
