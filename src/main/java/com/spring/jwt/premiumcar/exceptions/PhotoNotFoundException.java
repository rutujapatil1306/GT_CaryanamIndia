package com.spring.jwt.premiumcar.exceptions;
public class PhotoNotFoundException extends RuntimeException {
    public PhotoNotFoundException(String message) {
        super(message);
    }
}