package dev.xs3sync.project;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class ProjectDestination {
    private final @Nonnull String bucket;
    private final @Nonnull String region;
    private final @Nullable String accessKeyId;
    private final @Nullable String secretAccessKey;
    private final @Nullable String profile;
    private final @Nullable String endpoint;

    public ProjectDestination(
        final @Nonnull String bucket,
        final @Nonnull String region,
        final @Nullable String accessKeyId,
        final @Nullable String secretAccessKey,
        final @Nullable String profile,
        final @Nullable String endpoint
    ) {
        this.bucket = bucket;
        this.region = region;
        this.accessKeyId = accessKeyId;
        this.secretAccessKey = secretAccessKey;
        this.profile = profile;
        this.endpoint = endpoint;
    }

    public @Nonnull String getBucket() {
        return bucket;
    }

    public @Nonnull String getRegion() {
        return region;
    }

    public @Nullable String getAccessKeyId() {
        return accessKeyId;
    }

    public @Nullable String getSecretAccessKey() {
        return secretAccessKey;
    }

    public @Nullable String getProfile() {
        return profile;
    }

    public @Nullable String getEndpoint() {
        return endpoint;
    }
}
