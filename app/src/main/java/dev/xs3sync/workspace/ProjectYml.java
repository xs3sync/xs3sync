package dev.xs3sync.workspace;

import jakarta.annotation.Nonnull;

import java.util.List;

record ProjectYml(
    @Nonnull String source,
    @Nonnull Destination destination,
    @Nonnull List<String> include,
    @Nonnull List<String> exclude
) {
    public record Destination(
        @Nonnull String bucket,
        @Nonnull String region,
        @Nonnull String accessKeyId,
        @Nonnull String secretAccessKey,
        @Nonnull String endpoint
    ) {
    }
}
