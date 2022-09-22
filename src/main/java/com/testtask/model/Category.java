package com.testtask.model;

import com.testtask.exception.CategorizerException;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

import static com.testtask.config.Constants.KEYWORDS_SIZE_MAX_LIMIT;
import static com.testtask.config.Constants.KEYWORDS_SIZE_MIN_LIMIT;

@Slf4j
public record Category(String name, List<CategoryWord> keywords) {

    public Category {
        if (keywords.size() < KEYWORDS_SIZE_MIN_LIMIT) {
            var msg = String.format("Validation failed: at least %d keyword required", KEYWORDS_SIZE_MIN_LIMIT);
            log.error(msg);
            throw new CategorizerException(msg);
        }
        if (keywords.size() > KEYWORDS_SIZE_MAX_LIMIT) {
            var msg = String.format("Validation failed: %d keywords max", KEYWORDS_SIZE_MAX_LIMIT);
            log.error(msg);
            throw new CategorizerException(msg);
        }
    }

    @Override
    public List<CategoryWord> keywords() {
        return Collections.unmodifiableList(keywords);
    }
}
