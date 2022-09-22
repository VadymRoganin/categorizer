package com.testtask.model;

import com.testtask.exception.CategorizerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryWordTest {

    @Test
    public void shouldThrowExceptionWhenLimitExceeded() {
        Assertions.assertThrows(CategorizerException.class, () -> new CategoryWord("yes it is more than six words!"));
    }
}
