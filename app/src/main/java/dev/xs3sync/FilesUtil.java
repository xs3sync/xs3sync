package dev.xs3sync;

import jakarta.annotation.Nonnull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class FilesUtil {

    public @Nonnull InputStream getInputStreamFromResource(final @Nonnull String path) {
        final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);

        if (inputStream == null) {
            throw new NoSuchElementException(path);
        }

        return inputStream;
    }

    public boolean exists(
        final @Nonnull Path path,
        final @Nonnull LinkOption... options
    ) {
        return Files.exists(path, options);
    }

    public void copy(
        final @Nonnull InputStream inputStream,
        final @Nonnull Path target,
        final @Nonnull CopyOption... options
    ) {
        try {
            Files.createDirectories(target.getParent());
            Files.copy(inputStream, target, options);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public @Nonnull Stream<Path> walk(
        final @Nonnull Path start,
        final @Nonnull FileVisitOption... options
    ) {
        try {
            return Files.walk(start, options);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public @Nonnull Stream<Path> list(final @Nonnull Path dir) {
        try {
            return Files.list(dir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean isDirectory(final @Nonnull Path path, final @Nonnull LinkOption... options) {
        return Files.isDirectory(path, options);
    }

    public void createDirectories(final @Nonnull Path dir, final @Nonnull FileAttribute<?>... attrs) {
        try {
            Files.createDirectories(dir, attrs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public @Nonnull Path getFileName(final @Nonnull Path path) {
        return getFileName(path, false);
    }

    public @Nonnull Path getFileName(final @Nonnull Path path, boolean noExtension) {
        final Path fileName = path.getFileName();

        if (fileName == null) {
            throw new IllegalArgumentException("Ścieżka nie zawiera nazwy pliku: " + path);
        }

        final String name = fileName.toString();
        if (noExtension) {
            int dotIndex = name.lastIndexOf('.');
            if (dotIndex > 0) {
                return Path.of(name.substring(0, dotIndex));
            }
        }
        return Path.of(name);
    }
}