package dev.xs3sync;

import java.io.InputStream;

public class FileUtil {

    public FileUtil() {

    }

    /**
     * Otwiera InputStream do pliku znajdującego się w katalogu resources.
     * Przykład: jeśli w resources jest plik "config/default.project.yml",
     * to wywołanie zwróci InputStream do tego pliku.
     */
    public InputStream getInputStreamFromResource(String resourcePath) {
        // Pobranie InputStream przez ClassLoader
        InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);

        // Sprawdzenie czy plik istnieje
        return Objects.requireNonNull(is, "Resource not found: " + resourcePath);
    }
}