package dev.xs3sync.commands;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
    name = "xs3sync",
    mixinStandardHelpOptions = true,
    version = "xs3sync 1.0",
    description = "Narzędzie do synchronizacji z S3"
)
public class Xs3SyncCommand extends BaseCommand {

    public static void main(String[] args) {
        final CommandLine cmd = new CommandLine(new Xs3SyncCommand());
        cmd.addSubcommand("sync", new SyncCommand());
        cmd.execute(args);
    }

    @Override
    public void run() {
        init();
    }
}
