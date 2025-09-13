package com.spring.jwt.Car.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarResponseDto<T> {

    private String message;
    private T list;
    private String exception;
    private long totalNoOfCars;

    public static<T> CarResponseDto<T> success(String message, T list, long totalNoOfCars)
    {
        return new CarResponseDto<>(message, list, null, totalNoOfCars);
    }

    public static<T> CarResponseDto<T> error(String message, String exception, long totalNoOfCars)
    {
        return new CarResponseDto<>(message, null, exception, totalNoOfCars);
    }



}
