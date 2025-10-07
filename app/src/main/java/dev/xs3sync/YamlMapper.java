package dev.xs3sync;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import jakarta.annotation.Nonnull;

import java.io.File;
import java.io.IOException;

public class YamlMapper {

    private final @Nonnull ObjectMapper mapper;

    public YamlMapper() {
        this.mapper = new ObjectMapper(new YAMLFactory());
    }

    public <T> @Nonnull T readValue(
        final @Nonnull File src,
        final @Nonnull Class<T> valueType
    ) {
        try {
            return mapper.readValue(src, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void write(final @Nonnull T data, final @Nonnull String path) {
        try {
            mapper.writeValue(new File(path), data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
