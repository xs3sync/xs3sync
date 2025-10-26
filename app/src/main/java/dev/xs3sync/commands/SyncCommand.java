package dev.xs3sync.commands;

import picocli.CommandLine.Command;

@Command(
    name = "sync",
    description = "Synchronizuje pliki między lokalnym a S3"
)
final class SyncCommand extends BaseCommand {
    @Override
    void handleCommand() {
        services.syncService()
            .sync(services.projectRepository().get());
    }
}
