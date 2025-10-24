package dev.xs3sync.project;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.List;

public class Project {
    final @Nonnull Integer version;
    final @Nonnull String path;
    final @Nonnull Boolean fetched;
    final @Nonnull Destination destination;
    final @Nonnull List<String> include;
    final @Nonnull List<String> exclude;

    public Project(
        final @Nonnull Integer version,
        final @Nonnull String path,
        final @Nonnull Boolean fetched,
        final @Nonnull Destination destination,
        final @Nonnull List<String> include,
        final @Nonnull List<String> exclude
    ) {
        this.version = version;
        this.path = path;
        this.fetched = fetched;
        this.destination = destination;
        this.include = include;
        this.exclude = exclude;
    }

    public @Nonnull String getPath() {
        return path;
    }

    public @Nonnull Boolean getFetched() {
        return fetched;
    }

    public @Nonnull Destination getDestination() {
        return destination;
    }

    public @Nonnull List<String> getInclude() {
        return include;
    }

    public @Nonnull List<String> getExclude() {
        return exclude;
    }

    /**
     * Destination configuration for the project.
     */
    public static class Destination {
        final @Nonnull String bucket;
        final @Nonnull String region;
        final @Nullable String accessKeyId;
        final @Nullable String secretAccessKey;
        final @Nullable String profile;
        final @Nullable String endpoint;

        public Destination(
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
}
