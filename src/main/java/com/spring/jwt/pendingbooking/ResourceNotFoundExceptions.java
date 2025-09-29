package com.spring.jwt.pendingbooking;

public class ResourceNotFoundExceptions extends RuntimeException {
    public ResourceNotFoundExceptions(String message) {
        super(message);
    }
}

