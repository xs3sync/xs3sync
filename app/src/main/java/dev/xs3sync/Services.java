package dev.xs3sync;

import dev.xs3sync.fetch.FetchService;
import dev.xs3sync.init.InitService;
import dev.xs3sync.storage.StorageUtil;
import dev.xs3sync.workspace.Workspace;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

@SuppressWarnings("ALL")
public class Services {
    private final @Nonnull String workingDirectory;
    private @Nullable Workspace workspace = null;
    private @Nullable PathUtil pathUtil = null;
    private @Nullable FilesUtil filesUtil = null;
    private @Nullable YamlMapper yamlMapper = null;
    private @Nullable JsonMapper jsonMapper = null;
    private @Nullable StorageUtil storageUtil = null;
    private @Nullable InitService initService = null;
    private @Nullable FetchService fetchService = null;

    public Services(final @Nonnull String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public @Nonnull String workingDirectory() {
        return this.workingDirectory;
    }

    public synchronized void setWorkspace(final @Nonnull Workspace workspace) {
        this.workspace = workspace;
    }

    public synchronized @Nonnull Workspace workspace() {
        if (workspace == null) {
            throw new IllegalStateException("Workspace not initialized yet.");
        }

        return workspace;
    }

    public synchronized @Nonnull PathUtil pathUtil() {
        if (pathUtil == null) {
            pathUtil = new PathUtil();
        }

        return pathUtil;
    }

    public synchronized @Nonnull FilesUtil filesUtil() {
        if (filesUtil == null) {
            filesUtil = new FilesUtil();
        }

        return filesUtil;
    }

    public synchronized @Nonnull YamlMapper yamlMapper() {
        if (yamlMapper == null) {
            yamlMapper = new YamlMapper();
        }

        return yamlMapper;
    }

    public synchronized @Nonnull JsonMapper jsonMapper() {
        if (jsonMapper == null) {
            jsonMapper = new JsonMapper();
        }

        return jsonMapper;
    }

    public synchronized @Nonnull StorageUtil storageUtil() {
        if (storageUtil == null) {
            storageUtil = new StorageUtil();
        }

        return storageUtil;
    }

    public synchronized @Nonnull InitService initService() {
        if (initService == null) {
            initService = new InitService(
                pathUtil(),
                filesUtil(),
                yamlMapper()
            );
        }

        return initService;
    }

    public synchronized @Nonnull FetchService fetchService() {
        if (fetchService == null) {
            fetchService = new FetchService();
        }

        return fetchService;
    }
}
