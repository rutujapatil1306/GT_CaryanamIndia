package com.spring.jwt.BrandData.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BrandResponseDto<T> {

    private String message;
    private T list;
    private String exception;

    public BrandResponseDto(String message) {
    }


    public static<T> BrandResponseDto<T> success (String message, T list){
        return new BrandResponseDto<>(message, list, null);
    }

    public static<T> BrandResponseDto<T> error (String message, String exception){
        return new BrandResponseDto<>(message, null, exception);
    }

    public static<T> BrandResponseDto<T> error (String message){
        return new BrandResponseDto<>(message);
    }


}
