package dev.xs3sync.commands;

import java.io.File;

import jakarta.annotation.Nonnull;

import dev.xs3sync.Services;
import dev.xs3sync.configuration.Configuration;
import picocli.CommandLine.Option;

public abstract class BaseCommand implements Runnable
{
    @Option(names = { "-v", "--verbose" })
    boolean verbose;

    @Option(names = { "-c", "--config" }, description = "Ścieżka do pliku konfiguracyjnego")
    private File configFile;

    protected void init()
    {
        final Configuration configuration = new Configuration(configFile);

        Services.
        System.out.printf("init ...");
    }
}