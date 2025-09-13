package com.spring.jwt.Car.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarResponseDto2<T> {

    String message;
    T car;
    String exception;

    public static<T> CarResponseDto2<T> response(String message, T car)
    {
        return new CarResponseDto2<>(message, car, null);
    }
}
