package com.spring.jwt.premiumcar.exceptions;
public class PhotosNotFoundException extends RuntimeException {
    public PhotosNotFoundException(String message) {
        super(message);
    }
}