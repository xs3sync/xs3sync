package dev.xs3sync.commands;

import picocli.CommandLine.Command;

@Command(
    name = "sync",
    description = "Synchronizuje pliki między lokalnym a S3"
)
final class SyncCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("Rozpoczynam synchronizację...");
        // tutaj logika synchronizacji
    }
}
