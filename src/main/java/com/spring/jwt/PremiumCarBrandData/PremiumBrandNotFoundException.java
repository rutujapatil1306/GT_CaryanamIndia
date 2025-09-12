package com.spring.jwt.PremiumCarBrandData;

public class PremiumBrandNotFoundException extends RuntimeException {
    public PremiumBrandNotFoundException(String id) {
        super("Premium brand not found with id: " + id);
    }
}

