package dev.xs3sync;

import jakarta.annotation.Nonnull;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;
import java.util.List;

public class Bucket {
    private final @Nonnull String name;
    private final @Nonnull S3Client client;

    private Bucket(
        final @Nonnull String name,
        final @Nonnull S3Client client
    ) {
        this.name = name;
        this.client = client;
    }

    public static @Nonnull Bucket createWithBasicCredentials(
        final @Nonnull String name,
        final @Nonnull String region,
        final @Nonnull String accessKey,
        final @Nonnull String secretKey
    ) {
        final S3Client client = S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(accessKey, secretKey)
                )
            )
            .build();

        return new Bucket(name, client);
    }

    public static @Nonnull Bucket createWithProfileCredentials(
        final @Nonnull String name,
        final @Nonnull String region,
        final @Nonnull String profile
    ) {
        final S3Client client = S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider(ProfileCredentialsProvider.create(profile))
            .build();

        return new Bucket(name, client);
    }

    public void putObject(
        final @Nonnull String key,
        final @Nonnull InputStream inputStream,
        final @Nonnull Long contentLength
    ) {
        final PutObjectRequest request = PutObjectRequest.builder()
            .bucket(name)
            .key(key)
            .build();

        client.putObject(request, RequestBody.fromInputStream(inputStream, contentLength));
    }

    public @Nonnull List<S3Object> listObjects() {
        final ListObjectsV2Request request = ListObjectsV2Request.builder()
            .bucket(name)
            .build();

        return client.listObjectsV2(request).contents();
    }

    // public @Nonnull InputStream getInputStream(final @Nonnull String path) {
    //     GetObjectRequest request = GetObjectRequest.builder()
    //         .bucket(bucketName)
    //         .key(path)
    //         .build();

    //     ResponseInputStream<GetObjectResponse> response = s3Client.getObject(request);
    //     return response; // ResponseInputStream jest InputStreamem
    // }

    public @Nonnull ResponseInputStream<GetObjectResponse> getObject(final @Nonnull String key) {
        final GetObjectRequest request = GetObjectRequest.builder()
            .bucket(name)
            .key(key)
            .build();

        return client.getObject(request);
    }

    public void close() {
        client.close();
    }
}