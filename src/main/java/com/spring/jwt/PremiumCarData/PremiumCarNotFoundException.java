package com.spring.jwt.PremiumCarData;

public class PremiumCarNotFoundException extends RuntimeException {
    public PremiumCarNotFoundException(String message) {
        super(message);
    }
}
