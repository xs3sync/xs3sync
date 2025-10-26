package dev.xs3sync;

import dev.xs3sync.encryption.EncryptionService;
import dev.xs3sync.fetch.FetchService;
import dev.xs3sync.init.InitService;
import dev.xs3sync.project.ProjectRepository;
import dev.xs3sync.storage.StorageUtil;
import dev.xs3sync.sync.SyncService;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.nio.file.Path;

public class Services {
    private final @Nonnull String workingDirectory;
    private @Nullable PathUtil pathUtil = null;
    private @Nullable FilesUtil filesUtil = null;
    private @Nullable YamlMapper yamlMapper = null;
    private @Nullable StorageUtil storageUtil = null;
    private @Nullable ProjectRepository projectRepository = null;
    private @Nullable InitService initService = null;
    private @Nullable FetchService fetchService = null;
    private @Nullable SyncService syncService = null;
    private @Nullable EncryptionService encryptionService = null;

    public Services(final @Nonnull String workingDirectory) {
        this.pathUtil = new PathUtil();
        this.workingDirectory = pathUtil.expand(Path.of(workingDirectory)).toString();
    }

    public @Nonnull String workingDirectory() {
        return this.workingDirectory;
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

    public synchronized @Nonnull StorageUtil storageUtil() {
        if (storageUtil == null) {
            storageUtil = new StorageUtil();
        }

        return storageUtil;
    }

    public synchronized @Nonnull InitService initService() {
        if (initService == null) {
            initService = new InitService(
                projectRepository(),
                filesUtil(),
                storageUtil()
            );
        }

        return initService;
    }

    public synchronized @Nonnull FetchService fetchService() {
        if (fetchService == null) {
            fetchService = new FetchService(
                projectRepository(),
                filesUtil(),
                storageUtil()
            );
        }

        return fetchService;
    }

    public synchronized @Nonnull ProjectRepository projectRepository() {
        if (projectRepository == null) {
            projectRepository = new ProjectRepository(
                workingDirectory,
                pathUtil(),
                filesUtil(),
                yamlMapper()
            );
        }

        return projectRepository;
    }

    public synchronized @Nonnull SyncService syncService() {
        if (syncService == null) {
            syncService = new SyncService(
                filesUtil(),
                storageUtil(),
                projectRepository()
            );
        }

        return syncService;
    }

    public synchronized @Nonnull EncryptionService encryptionService() {
        if (encryptionService == null) {
            encryptionService = new EncryptionService();
        }

        return encryptionService;
    }
}
