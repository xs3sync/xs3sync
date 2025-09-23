package dev.xs3sync.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Nonnull;

public class Configuration
{
    private final List<Project> projects = new ArrayList<>();

    public Configuration(final @Nonnull File configFile)
    {

    }
}