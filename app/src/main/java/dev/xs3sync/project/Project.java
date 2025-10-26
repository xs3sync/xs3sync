package dev.xs3sync.project;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Project {
    final @Nonnull Integer version;
    final @Nonnull String path;
    @Nonnull
    Boolean fetched;
    final @Nonnull Destination destination;
    final @Nonnull List<String> include;
    final @Nonnull List<String> exclude;
    final @Nonnull List<FileMetadata> files = new ArrayList<>();

    public Project(
        final @Nonnull Integer version,
        final @Nonnull String path,
        final @Nonnull Boolean fetched,
        final @Nonnull Destination destination,
        final @Nonnull List<String> include,
        final @Nonnull List<String> exclude,
        final @Nonnull List<FileMetadata> files
    ) {
        this.version = version;
        this.path = path;
        this.fetched = fetched;
        this.destination = destination;
        this.include = include;
        this.exclude = exclude;
        this.files.addAll(files);
    }

    public @Nonnull String getPath() {
        return path;
    }

    public @Nonnull Boolean getFetched() {
        return fetched;
    }

    public void setFetched(final @Nonnull Boolean fetched) {
        this.fetched = fetched;
    }

    @Nonnull
    public List<FileMetadata> getFiles() {
        return files;
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

    public void cleanFiles() {
        files.clear();
    }

    public void addFile(
        final @Nonnull String path,
        final @Nonnull Long modifiedAt
    ) {
        files.add(new FileMetadata(path, modifiedAt));
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

        Destination(
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

    /**
     * Metadata for a file in the project.
     */
    public static class FileMetadata {
        final @Nonnull String path;
        final @Nonnull Long modifiedAt;

        FileMetadata(
            final @Nonnull String path,
            final @Nonnull Long modifiedAt
        ) {
            this.path = path;
            this.modifiedAt = modifiedAt;
        }

        public @Nonnull String getPath() {
            return path;
        }

        public @Nonnull Long getModifiedAt() {
            return modifiedAt;
        }
    }
}
