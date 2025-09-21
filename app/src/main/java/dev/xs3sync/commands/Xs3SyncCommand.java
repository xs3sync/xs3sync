package dev.xs3sync.commands;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.File;

import static picocli.CommandLine.Option;

@Command(
    name = "xs3sync",
    mixinStandardHelpOptions = true,
    version = "xs3sync 1.0",
    description = "Narzędzie do synchronizacji z S3")
public class Xs3SyncCommand implements Runnable {

    @Option(names = {"-c", "--config"}, description = "Ścieżka do pliku konfiguracyjnego")
    private File configFile;

    public static void main(String[] args) {
        CommandLine cmd = new CommandLine(new Xs3SyncCommand());
        cmd.addSubcommand("sync", new SyncCommand());
        cmd.execute(args);
    }

    @Override
    public void run() {
        System.out.println("Wpisz 'xs3sync sync' aby zsynchronizować pliki.");
    }
}
