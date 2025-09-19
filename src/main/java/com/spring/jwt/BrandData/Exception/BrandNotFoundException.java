package com.spring.jwt.BrandData.Exception;

public class BrandNotFoundException extends RuntimeException{

    public BrandNotFoundException(String message)
    {
        super(message);
    }
}
