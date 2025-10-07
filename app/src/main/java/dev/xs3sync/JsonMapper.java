package dev.xs3sync;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;

import java.io.File;
import java.io.IOException;

public class JsonMapper {

    private final @Nonnull ObjectMapper mapper;

    public JsonMapper() {
        this.mapper = new ObjectMapper(new JsonFactory());
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
}