package dev.xs3sync.commands;

import dev.xs3sync.Services;
import dev.xs3sync.workspace.Workspace;
import picocli.CommandLine.Option;

public abstract class BaseCommand implements Runnable {
    @Option(names = {"-v", "--verbose"})
    private boolean verboseParameter;

    @Option(names = {"-c", "--workspace"}, description = "Ścieżka do katalogu roboczego")
    private String workspaceParameter;

    protected Workspace workspace;

    protected Services services;

    protected void init() {
        workspace = new Workspace(
            this.workspaceParameter,
            Services.pathUtil(),
            Services.filesUtil(),
            Services.yamlMapper()
        );

        Services.setWorkspace(workspace);
    }
}