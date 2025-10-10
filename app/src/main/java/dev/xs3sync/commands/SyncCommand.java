package dev.xs3sync.commands;

import dev.xs3sync.project.Project;
import picocli.CommandLine.Command;

@Command(
    name = "sync",
    description = "Synchronizuje pliki między lokalnym a S3"
)
final class SyncCommand extends BaseCommand {

    @Override
    public void run() {
        init();

        // final Project project = services.projectRepository().get();
        // services.
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
