package dev.xs3sync.workspace;

import jakarta.annotation.Nullable;

import java.util.List;

record ProjectYml(
    @Nullable String source,
    @Nullable DestinationYml destination,
    @Nullable List<String> include,
    @Nullable List<String> exclude
) {
    public record DestinationYml(
        @Nullable String bucket,
        @Nullable String region,
        @Nullable String accessKeyId,
        @Nullable String secretAccessKey,
        @Nullable String endpoint
    ) {
    }
}
