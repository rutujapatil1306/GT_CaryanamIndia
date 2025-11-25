package com.spring.jwt.Car.Exception;

public class DuplicateCarEntryException extends RuntimeException {
    public DuplicateCarEntryException(String message) {
        super(message);
    }
}
