package dev.xs3sync;

import jakarta.annotation.Nonnull;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;
import java.util.List;

public class Bucket {
    private final @Nonnull S3Client s3Client;
    private final @Nonnull String name;

    public Bucket(
        final @Nonnull String bucketName,
        final @Nonnull String region,
        final @Nonnull String accessKey,
        final @Nonnull String secretKey
    ) {
        this.s3Client = S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(accessKey, secretKey)
                )
            )
            .build();

        this.name = bucketName;
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

        s3Client.putObject(request, RequestBody.fromInputStream(inputStream, contentLength));
    }

    public @Nonnull List<S3Object> listObjects() {
        final ListObjectsV2Request request = ListObjectsV2Request.builder()
            .bucket(name)
            .build();

        return s3Client.listObjectsV2(request).contents();
    }

    public @Nonnull ResponseInputStream<GetObjectResponse> getObject(final @Nonnull String key) {
        final GetObjectRequest request = GetObjectRequest.builder()
            .bucket(name)
            .key(key)
            .build();

        return s3Client.getObject(request);
    }

    public void close() {
        s3Client.close();
    }
}