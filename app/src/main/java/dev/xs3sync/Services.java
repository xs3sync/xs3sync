package dev.xs3sync;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import dev.xs3sync.workspace.Workspace;

public class Services {
    private static @Nullable Workspace workspace = null;
    private static @Nullable PathUtil pathUtil = null;

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
}
