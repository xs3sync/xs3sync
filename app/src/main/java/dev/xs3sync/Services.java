package dev.xs3sync;

import dev.xs3sync.workspace.Workspace;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class Services {
    private static @Nullable Workspace workspace = null;
    private static @Nullable PathUtil pathUtil = null;
    private static @Nullable FilesUtil filesUtil = null;
    private static @Nullable YamlMapper yamlMapper = null;

    public synchronized static void setWorkspace(final @Nonnull Workspace workspace) {
        Services.workspace = workspace;
    }

    public synchronized static @Nonnull Workspace workspace() {
        if (workspace == null) {
            throw new IllegalStateException("Workspace not initialized yet.");
        }

        return workspace;
    }

    public synchronized static @Nonnull PathUtil pathUtil() {
        if (pathUtil == null) {
            pathUtil = new PathUtil();
        }

        return pathUtil;
    }

    public synchronized static @Nonnull FilesUtil filesUtil() {
        if (filesUtil == null) {
            filesUtil = new FilesUtil();
        }

        return filesUtil;
    }

    public synchronized static @Nonnull YamlMapper yamlMapper() {
        if (yamlMapper == null) {
            yamlMapper = new YamlMapper();
        }

        return yamlMapper;
    }
}
