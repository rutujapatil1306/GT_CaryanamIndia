package com.spring.jwt.BrandData.Exception;

public class BrandAlreadyExistsException extends RuntimeException{

    public BrandAlreadyExistsException(String message)
    {
        super(message);
    }
}
