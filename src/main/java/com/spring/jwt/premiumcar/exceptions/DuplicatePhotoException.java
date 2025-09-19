package com.spring.jwt.premiumcar.exceptions;

public class DuplicatePhotoException extends RuntimeException {
    public DuplicatePhotoException(String message) {
        super(message);
    }
}
