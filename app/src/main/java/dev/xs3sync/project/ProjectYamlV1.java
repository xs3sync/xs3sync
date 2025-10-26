package dev.xs3sync.project;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.List;

public record ProjectYamlV1(
    @Nonnull Integer version,
    @Nonnull Boolean fetched,
    @Nonnull DestinationYml destination,
    @Nonnull List<FileMetadata> files
) {
    public record DestinationYml(
        @Nonnull String bucket,
        @Nonnull String region,
        @Nullable String accessKeyId,
        @Nullable String secretAccessKey,
        @Nullable String profile,
        @Nullable String endpoint
    ) {
    }

    public record FileMetadata(
        @Nonnull String path,
        @Nonnull Long modifiedAt
    ) {
    }
}
