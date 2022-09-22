package com.testtask.exception;

/**
 * Generic exception
 */
public class CategorizerException extends RuntimeException {
    public CategorizerException(String message) {
        super(message);
    }

    public CategorizerException(String message, Throwable cause) {
        super(message, cause);
    }
}
