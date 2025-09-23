package com.spring.jwt.CarPhoto.Exception;

public class DuplicatePhotoException extends RuntimeException {
    public DuplicatePhotoException(String message) {
        super(message);
    }
}
