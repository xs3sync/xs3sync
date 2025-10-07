package dev.xs3sync.commands;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
    name = "init",
    description = "Inicjuje katalog do synchronizacji"
)
final class InitCommand extends BaseCommand {

    @CommandLine.Option(names = {"--bucket"}, required = true)
    private String bucket;

    @CommandLine.Option(names = {"--region"}, required = true)
    private String region;

    @CommandLine.Option(names = {"--access-key-id"})
    private String accessKeyId;

    @CommandLine.Option(names = {"--secret-access-key"})
    private String secretAccessKey;

    @CommandLine.Option(names = {"--profile"})
    private String profile;

    @CommandLine.Option(names = {"--endpoint"})
    private String endpoint;

    @Override
    public void run() {
        init();

        services.initService().init(
            services.workingDirectory()
        );

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
