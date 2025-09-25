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
    )
        throws IOException {

        return mapper.readValue(src, valueType);
    }
}
