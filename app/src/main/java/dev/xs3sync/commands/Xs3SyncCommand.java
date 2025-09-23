package dev.xs3sync.commands;

import dev.xs3sync.configuration.Configuration;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.File;

import static picocli.CommandLine.Option;

import jakarta.annotation.Nonnull;

@Command(
    name = "xs3sync",
    mixinStandardHelpOptions = true,
    version = "xs3sync 1.0",
    description = "Narzędzie do synchronizacji z S3")
public class Xs3SyncCommand extends BaseCommand {

    public static void main(String[] args) {
        CommandLine cmd = new CommandLine(new Xs3SyncCommand());
        cmd.addSubcommand("sync", new SyncCommand());
        cmd.execute(args);
    }

    @Override
    public void run() {
        System.out.printf("test");
        // System.out.printf("test");
        // final @Nonnull Configuration configuration = new Configuration(conf);

        // System.out.println("Wpisz 'xs3sync sync' aby zsynchronizować pliki.");
    }
}
