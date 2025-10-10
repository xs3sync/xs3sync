package dev.xs3sync.project;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public record ProjectYaml(
    @Nonnull DestinationYml destination
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
}
