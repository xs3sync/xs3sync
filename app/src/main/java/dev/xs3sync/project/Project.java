package dev.xs3sync.project;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Project {
    final @Nonnull String path;
    final @Nonnull Destination destination;
    final @Nonnull List<String> include;
    final @Nonnull List<String> exclude;

    public Project(
        final @Nonnull String path,
        final @Nonnull Destination destination,
        final @Nonnull List<String> include,
        final @Nonnull List<String> exclude
    ) {
        this.path = path;
        this.destination = destination;
        this.include = include;
        this.exclude = exclude;
    }

    public @Nonnull String getPath() {
        return path;
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

    // builder ---------------------------------------------------------------------------------------------------------
    public static Builder builder(
        final @Nonnull String path
    ) {
        return new Builder(path);
    }

    public static class Builder {
        private final @Nonnull String path;
        private @Nullable String destinationBucket;
        private @Nullable String destinationRegion;
        private @Nullable String destinationAccessKeyId;
        private @Nullable String destinationSecretAccessKey;
        private @Nullable String destinationProfile;
        private @Nullable String destinationEndpoint;
        private final @Nonnull List<String> include = new ArrayList<>();
        private final @Nonnull List<String> exclude = new ArrayList<>();

        public Builder(final @Nonnull String path) {
            this.path = path;
        }

        public @Nonnull Builder setDestination(
            final @Nonnull String bucket,
            final @Nonnull String region,
            final @Nonnull String profile
        ) {
            this.destinationBucket = bucket;
            this.destinationRegion = region;
            this.destinationProfile = profile;
            return this;
        }

        public @Nonnull Builder setInclude(final @Nonnull List<String> include) {
            this.include.clear();
            this.include.addAll(include);
            return this;
        }

        public @Nonnull Builder setExclude(final @Nonnull List<String> exclude) {
            this.exclude.clear();
            this.exclude.addAll(exclude);
            return this;
        }

        public @Nonnull Project build() {
            if (destinationBucket == null) {
                throw new IllegalStateException("Pole 'destination.bucket' jest wymagane");
            }
            if (destinationRegion == null) {
                throw new IllegalStateException("Pole 'destination.region' jest wymagane");
            }
            if (destinationProfile == null && (destinationAccessKeyId == null || destinationSecretAccessKey == null)) {
                throw new IllegalStateException("Pole 'destination.profile' jest wymagane lub destination.accessKeyId, destination.secretAccessKey jest wymagane.");
            }

            return new Project(
                path,
                new Destination(
                    destinationBucket,
                    destinationRegion,
                    destinationAccessKeyId,
                    destinationSecretAccessKey,
                    destinationProfile,
                    destinationEndpoint
                ),
                include,
                exclude
            );
        }
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
