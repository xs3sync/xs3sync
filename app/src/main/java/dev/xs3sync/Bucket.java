package dev.xs3sync;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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

    public static Bucket createWithParameters(
        final @Nonnull String bucket,
        final @Nonnull String region,
        final @Nullable String profile,
        final @Nullable String accessKeyId,
        final @Nullable String secretAccessKey,
        final @Nullable String endpoint
    ) {
        if (profile != null) {
            return createWithProfileCredentials(bucket, region, profile);
        } else {
            throw new RuntimeException("Implements accessKeyId and secretAccessKey bucket.");
        }
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

        final InputStream countingStream = new FilterInputStream(inputStream) {
            private final AtomicLong bytesRead = new AtomicLong(0);
            private final int barWidth = 50;

            @Override
            public int read() throws IOException {
                int b = super.read();
                if (b != -1) {
                    updateProgress(1);
                }
                return b;
            }

            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                int n = super.read(b, off, len);
                if (n > 0) {
                    updateProgress(n);
                }
                return n;
            }

            private void updateProgress(int n) {
                long totalRead = bytesRead.addAndGet(n);
                double progress = (double) totalRead / contentLength;
                int filled = (int) (progress * barWidth);

                StringBuilder bar = new StringBuilder();
                bar.append("\r %s[".formatted(key));
                for (int i = 0; i < barWidth; i++) {
                    bar.append(i < filled ? '=' : ' ');
                }
                bar.append("] ");
                bar.append(String.format("%.2f%%", progress * 100));
                System.out.print(bar);
            }
        };

        client.putObject(request, RequestBody.fromInputStream(countingStream, contentLength));
    }

    public @Nonnull List<S3Object> listObjects() {
        final List<S3Object> objects = new ArrayList<>();
        ListObjectsV2Response response = null;
        String continuationToken = null;

        do {
            final ListObjectsV2Request.Builder requestBuilder = ListObjectsV2Request.builder()
                .bucket(name);

            if (continuationToken != null) {
                requestBuilder.continuationToken(continuationToken);
            }

            response = client.listObjectsV2(requestBuilder.build());
            objects.addAll(response.contents());
            continuationToken = response.nextContinuationToken();
        } while (response.isTruncated());

        return objects;
    }

    // public @Nonnull InputStream getInputStream(final @Nonnull String path) {
    //     GetObjectRequest request = GetObjectRequest.builder()
    //         .bucket(bucketName)
    //         .key(path)
    //         .build();

    //     ResponseInputStream<GetObjectResponse> response = s3Client.getObject(request);
    //     return response; // ResponseInputStream jest InputStreamem
    // }

    public void move(
        final @Nonnull String key,
        final @Nonnull String newKey
    ) {
        final CopyObjectRequest copyRequest = CopyObjectRequest.builder()
            .sourceBucket(name)
            .sourceKey(key)
            .destinationBucket(name)
            .destinationKey(newKey)
            .build();

        client.copyObject(copyRequest);

        final DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
            .bucket(name)
            .key(key)
            .build();

        client.deleteObject(deleteRequest);
    }

    public @Nonnull ResponseInputStream<GetObjectResponse> getObject(final @Nonnull String key) {
        final GetObjectRequest request = GetObjectRequest.builder()
            .bucket(name)
            .key(key)
            .build();

        final ResponseInputStream<GetObjectResponse> object = client.getObject(request);
        long contentLength = object.response().contentLength();

        return new ResponseInputStream<>(
            object.response(),
            new ProgressInputStream(object, contentLength, key)
        );
    }

    public void close() {
        client.close();
    }

    static class ProgressInputStream extends FilterInputStream {
        private final long totalBytes;
        private long bytesRead = 0;
        private final String fileName;
        private int lastDrawn = -1;

        protected ProgressInputStream(InputStream in, long totalBytes, String fileName) {
            super(in);
            this.totalBytes = totalBytes;
            this.fileName = fileName;
        }

        @Override
        public int read() throws IOException {
            int b = super.read();
            if (b != -1) updateProgress(1);
            return b;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            int n = super.read(b, off, len);
            if (n > 0) updateProgress(n);
            return n;
        }

        private void updateProgress(int bytes) {
            bytesRead += bytes;
            if (totalBytes <= 0) return;

            int percent = (int) ((bytesRead * 100) / totalBytes);
            if (percent != lastDrawn && percent % 2 == 0) { // aktualizuj co 2%
                int barWidth = 20;
                int filled = (int) (percent / (100.0 / barWidth));
                String bar = "[" + "#".repeat(filled) + " ".repeat(barWidth - filled) + "]";
                System.out.printf("\rPobieranie: %-24s %3d%% %s", bar, percent, fileName);
                lastDrawn = percent;
            }
        }

        @Override
        public void close() throws IOException {
            super.close();
            System.out.printf("\rPobieranie: [####################] 100%% %s ✅%n", fileName);
        }
    }
}
