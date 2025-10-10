package dev.xs3sync.commands;

import dev.xs3sync.Services;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import picocli.CommandLine.Option;

public abstract class BaseCommand implements Runnable {

    @Option(names = {"-v", "--verbose"})
    private @Nonnull Integer verbose = 0;

    @Option(names = {"-wd", "--working-directory"})
    private @Nullable String workingDirectory;

    protected Services services;

    protected void init() {
        services = new Services(
            workingDirectory != null ? workingDirectory : System.getProperty("user.dir")
        );
    }
}