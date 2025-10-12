package dev.xs3sync.commands;

import dev.xs3sync.sync.SyncService;
import picocli.CommandLine.Command;

@Command(
    name = "sync",
    description = "Synchronizuje pliki między lokalnym a S3"
)
final class SyncCommand extends BaseCommand {
    @Override
    public void run() {
        init();

        services.syncService().sync(services.projectRepository().get());
    }
}
