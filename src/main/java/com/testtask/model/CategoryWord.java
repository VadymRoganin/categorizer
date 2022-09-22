package com.testtask.model;

import com.testtask.exception.CategorizerException;
import lombok.extern.slf4j.Slf4j;

import static com.testtask.config.Constants.CATEGORY_WORD_LIMIT;

@Slf4j
public record CategoryWord(String word) {
    public CategoryWord {
        if (word.split("\\s+").length > CATEGORY_WORD_LIMIT) {
            var msg = String.format("Validation failed: %d words max", CATEGORY_WORD_LIMIT);
            log.error(msg);
            throw new CategorizerException(msg);
        }
    }
}
