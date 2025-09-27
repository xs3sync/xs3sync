package dev.xs3sync.commands;

import dev.xs3sync.Services;
import dev.xs3sync.SyncService;
import dev.xs3sync.workspace.Project;
import picocli.CommandLine.Command;

import java.nio.file.Path;

@Command(
    name = "sync",
    description = "Synchronizuje pliki między lokalnym a S3"
)
final class SyncCommand extends BaseCommand {

    @Override
    public void run() {
        init();

        System.out.println("Rozpoczynam synchronizację...");

        for (final Project project : workspace.getProjects()) {
            final SyncService syncService = new SyncService(Path.of(project.getSource()), Services.filesUtil());
            syncService.sync();
        }
    }
}
