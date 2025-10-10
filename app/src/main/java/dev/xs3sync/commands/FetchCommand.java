package dev.xs3sync.commands;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@SuppressWarnings({"NotNullFieldNotInitialized", "unused"})
@Command(
    name = "fetch",
    description = "Inicjuje katalog do synchronizacji"
)
final class FetchCommand extends BaseCommand {

    @Option(names = {"--bucket"}, required = true)
    private @Nonnull String bucket;

    @Option(names = {"--region"}, required = true)
    private @Nonnull String region;

    @Option(names = {"--access-key-id"})
    private @Nullable String accessKeyId;

    @Option(names = {"--secret-access-key"})
    private @Nullable String secretAccessKey;

    @Option(names = {"--profile"})
    private @Nullable String profile;

    @Option(names = {"--endpoint"})
    private @Nullable String endpoint;

    @Override
    public void run() {
        init();

        services.fetchService().fetch(
            services.workingDirectory(),
            bucket,
            region,
            accessKeyId,
            secretAccessKey,
            profile,
            endpoint
        );
    }
}
