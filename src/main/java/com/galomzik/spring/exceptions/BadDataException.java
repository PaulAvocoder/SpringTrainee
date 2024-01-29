package com.galomzik.spring.exceptions;

public class BadDataException extends RuntimeException {
    public BadDataException(String message) {
        super(message);
    }
}