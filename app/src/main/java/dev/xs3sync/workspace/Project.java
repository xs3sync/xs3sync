package dev.xs3sync.workspace;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class Project {
    private @Nonnull String id;
    private @Nonnull String source;
    private @Nonnull String destinationBucket;
    private @Nonnull String destinationRegion;
    private @Nullable String destinationAccessKeyId;
    private @Nullable String destinationSecretAccessKey;
    private @Nullable String destinationProfile;
    private @Nonnull String destinationEndpoint;
    private @Nonnull List<String> include;
    private @Nonnull List<String> exclude;

    private Project(
        final @Nonnull String id,
        final @Nonnull String source,
        final @Nonnull String destinationBucket,
        final @Nonnull String destinationRegion,
        final @Nullable String destinationAccessKeyId,
        final @Nullable String destinationSecretAccessKey,
        final @Nullable String destinationProfile,
        final @Nonnull String destinationEndpoint,
        final @Nonnull List<String> include,
        final @Nonnull List<String> exclude
    ) {
        this.id = id;
        this.source = source;
        this.destinationBucket = destinationBucket;
        this.destinationRegion = destinationRegion;
        this.destinationAccessKeyId = destinationAccessKeyId;
        this.destinationSecretAccessKey = destinationSecretAccessKey;
        this.destinationProfile = destinationProfile;
        this.destinationEndpoint = destinationEndpoint;
        this.include = include;
        this.exclude = exclude;
    }

    public @Nonnull String getId() {
        return id;
    }

    public @Nonnull String getSource() {
        return source;
    }

    public @Nonnull String getDestinationBucket() {
        return destinationBucket;
    }

    public @Nonnull String getDestinationRegion() {
        return destinationRegion;
    }

    public @Nullable String getDestinationAccessKeyId() {
        return destinationAccessKeyId;
    }

    public @Nullable String getDestinationSecretAccessKey() {
        return destinationSecretAccessKey;
    }

    public @Nullable String getDestinationProfile() {
        return destinationProfile;
    }

    public @Nonnull String getDestinationEndpoint() {
        return destinationEndpoint;
    }

    public @Nonnull List<String> getInclude() {
        return unmodifiableList(include);
    }

    public @Nonnull List<String> getExclude() {
        return unmodifiableList(exclude);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private @Nullable String id;
        private @Nullable String source;
        private @Nullable String destinationBucket;
        private @Nullable String destinationRegion;
        private @Nullable String destinationAccessKeyId;
        private @Nullable String destinationSecretAccessKey;
        private @Nullable String destinationProfile;
        private @Nullable String destinationEndpoint;
        private final @Nonnull List<String> include = new ArrayList<>();
        private final @Nonnull List<String> exclude = new ArrayList<>();

        public @Nonnull Builder id(final @Nonnull String id) {
            this.id = id;
            return this;
        }

        public @Nonnull Builder source(final @Nonnull String source) {
            this.source = source;
            return this;
        }

        public @Nonnull Builder destinationBucket(final @Nonnull String destinationBucket) {
            this.destinationBucket = destinationBucket;
            return this;
        }

        public @Nonnull Builder destinationRegion(final @Nonnull String destinationRegion) {
            this.destinationRegion = destinationRegion;
            return this;
        }

        public @Nonnull Builder destinationAccessKeyId(final @Nonnull String destinationAccessKeyId) {
            this.destinationAccessKeyId = destinationAccessKeyId;
            return this;
        }

        public @Nonnull Builder destinationSecretAccessKey(final @Nonnull String destinationSecretAccessKey) {
            this.destinationSecretAccessKey = destinationSecretAccessKey;
            return this;
        }

        public @Nonnull Builder destinationProfile(final @Nonnull String destinationProfile) {
            this.destinationProfile = destinationProfile;
            return this;
        }

        public @Nonnull Builder destinationEndpoint(final @Nonnull String destinationEndpoint) {
            this.destinationEndpoint = destinationEndpoint;
            return this;
        }

        public @Nonnull Builder include(final @Nonnull List<String> include) {
            this.include.clear();
            this.include.addAll(include);
            return this;
        }

        public @Nonnull Builder exclude(final @Nonnull List<String> exclude) {
            this.exclude.clear();
            this.exclude.addAll(exclude);
            return this;
        }

        public @Nonnull Project build() {
            if (id == null) {
                throw new IllegalStateException("Pole 'id' jest wymagane");
            }
            if (source == null) {
                throw new IllegalStateException("Pole 'source' jest wymagane");
            }
            if (destinationBucket == null) {
                throw new IllegalStateException("Pole 'destinationBucket' jest wymagane");
            }
            if (destinationRegion == null) {
                throw new IllegalStateException("Pole 'destinationRegion' jest wymagane");
            }
            if (destinationProfile == null && (destinationAccessKeyId == null || destinationSecretAccessKey == null)) {
                throw new IllegalStateException("Pole 'destinationProfile' jest wymagane lub destinationAccessKeyId, destinationSecretAccessKey jest wymagane.");
            }
            if (destinationAccessKeyId == null) {
                throw new IllegalStateException("Pole 'destinationAccessKeyId' jest wymagane");
            }
            if (destinationSecretAccessKey == null) {
                throw new IllegalStateException("Pole 'destinationSecretAccessKey' jest wymagane");
            }
            if (destinationEndpoint == null) {
                throw new IllegalStateException("Pole 'destinationEndpoint' jest wymagane");
            }

            return new Project(
                id,
                source,
                destinationBucket,
                destinationRegion,
                destinationAccessKeyId,
                destinationSecretAccessKey,
                destinationProfile,
                destinationEndpoint,
                include,
                exclude
            );
        }
    }
}
