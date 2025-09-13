package com.spring.jwt.Car.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarResponseDto<T> {

    String message;
    T list;
    String Exception;

    public static<T> UpdateCarResponseDto<T> response(String message, T list)
    {
        return new UpdateCarResponseDto<>(message, list, null);
    }
}
