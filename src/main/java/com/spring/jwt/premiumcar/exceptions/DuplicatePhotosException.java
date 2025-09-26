package com.spring.jwt.premiumcar.exceptions;

public class DuplicatePhotosException extends RuntimeException {
    public DuplicatePhotosException(String message) {
        super(message);
    }
}
