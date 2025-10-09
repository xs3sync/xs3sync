package dev.xs3sync.commands;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@SuppressWarnings({"NotNullFieldNotInitialized", "unused"})
@Command(
    name = "init",
    description = "Inicjuje katalog do synchronizacji"
)
final class InitCommand extends BaseCommand {

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

        services.initService().init(
            services.workingDirectory(),
            bucket,
            region,
            accessKeyId,
            secretAccessKey,
            profile,
            endpoint
        );

        // services.fetchService().fetch();

        // services.initService().init();
        // for (final Project project : workspace.getProjects()) {
        //     final String destinationRegion = project.getDestinationRegion();
        //     final @Nullable String destinationProfile = project.getDestinationProfile();

        //     if (destinationProfile == null) {
        //         throw new IllegalArgumentException("Destination profile of project %s is not set".formatted(project.getId()));
        //     }

        //     final Bucket bucket = Bucket.createWithProfileCredentials(
        //         project.getDestinationBucket(),
        //         destinationRegion,
        //         destinationProfile
        //     );

        //     final SyncService syncService = new SyncService(
        //         Path.of(project.getSource()),
        //         Services.filesUtil(),
        //         bucket,
        //         Services.storageUtil()
        //     );

        //     syncService.sync();
        // }
    }
}
