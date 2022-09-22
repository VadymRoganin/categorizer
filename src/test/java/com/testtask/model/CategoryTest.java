package com.testtask.model;

import com.testtask.exception.CategorizerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.stream.IntStream;

public class CategoryTest {
    @Test
    public void shouldThrowExceptionWhenUpperLimitExceeded() {
        Assertions.assertThrows(CategorizerException.class, () -> new Category("Category 1", IntStream.range(0, 1001)
                .mapToObj(i -> new CategoryWord(String.valueOf(i)))
                .toList()));
    }

    @Test
    public void shouldThrowExceptionWhenLowerLimitExceeded() {
        Assertions.assertThrows(CategorizerException.class, () -> new Category("Category 1", Collections.emptyList()));
    }
}
