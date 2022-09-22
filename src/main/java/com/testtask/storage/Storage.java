package com.testtask.storage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testtask.exception.CategorizerException;
import com.testtask.model.Category;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class Storage {

    private final String path;

    private final ObjectMapper mapper = new ObjectMapper();

    public Storage(String path) {
        this.path = path;
    }

    public void save(Object o) {
        String json;
        try {
            json = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(o);
        } catch (JsonProcessingException e) {
            var msg = "Error writing JSON";
            log.error(msg);
            throw new CategorizerException(msg, e);
        }

        try {
            log.info("Writing to file: {}", path);
            Files.write(Path.of(path), json.getBytes());
        } catch (IOException e) {
            var msg = "Error writing file";
            log.error(msg);
            throw new CategorizerException(msg, e);
        }
    }

    public List<Category> load() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info("Loading file: {}", path);
            return objectMapper.readValue(new File(path), new TypeReference<>() {});
        } catch (IOException e) {
            var msg = "Error reading file";
            log.error(msg);
            throw new CategorizerException(msg, e);
        }
    }
}
