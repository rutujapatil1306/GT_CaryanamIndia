package com.spring.jwt.CarView.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarViewResponseDto2<T> {


    private String message;
    private T carView;
    private String status;
    private int code;
    private String statusCode;
    private String exception;

    public CarViewResponseDto2 (String message, T carView, String status, HttpStatus httpStatus , String exception)
    {
        this.message = message;
        this.carView = carView;
        this.status = status;
        this.code = httpStatus.value();
        this.statusCode = httpStatus.name();
        this.exception = exception;
    }

}
