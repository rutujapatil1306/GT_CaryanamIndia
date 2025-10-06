package com.spring.jwt.premiumcarpendingbooking;
public class InvalidIdException extends RuntimeException {
    public InvalidIdException(String message) {
        super(message);
    }
}
