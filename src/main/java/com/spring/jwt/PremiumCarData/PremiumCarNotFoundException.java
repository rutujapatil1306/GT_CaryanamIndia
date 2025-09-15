package com.spring.jwt.PremiumCarData;

public class PremiumCarNotFoundException extends RuntimeException {
    public PremiumCarNotFoundException(String id) {
        super("Premium brand not found with id: " + id);
    }
}

