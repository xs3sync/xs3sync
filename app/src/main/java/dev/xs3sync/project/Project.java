package dev.xs3sync.project;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private final @Nonnull ProjectSource source;
    private final @Nonnull ProjectDestination destination;
    private final @Nonnull List<String> include;
    private final @Nonnull List<String> exclude;

    public Project(
        final @Nonnull ProjectSource source,
        final @Nonnull ProjectDestination destination,
        final @Nonnull List<String> include,
        final @Nonnull List<String> exclude
    ) {
        this.source = source;
        this.destination = destination;
        this.include = include;
        this.exclude = exclude;
    }

    // builder ---------------------------------------------------------------------------------------------------------
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private @Nullable String sourcePath;
        private @Nullable String destinationBucket;
        private @Nullable String destinationRegion;
        private @Nullable String destinationAccessKeyId;
        private @Nullable String destinationSecretAccessKey;
        private @Nullable String destinationProfile;
        private @Nullable String destinationEndpoint;
        private final @Nonnull List<String> include = new ArrayList<>();
        private final @Nonnull List<String> exclude = new ArrayList<>();

        public @Nonnull Builder setSourcePath(final @Nonnull String path) {
            this.sourcePath = path;
            return this;
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
            if (sourcePath == null) {
                throw new IllegalStateException("Pole 'source.path' jest nieustawione" );
            }
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
                new ProjectSource(sourcePath),
                new ProjectDestination(
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
}
