package com.spring.jwt.Car.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarResponse<T> {
    private String message;
    private T list;
    private String status;
    private int code;
    private String statusCode;
    private String exception;


    public CarResponse(String message,
                       T list,
                       String status,
                       HttpStatus httpStatus,
                       String exception
    ) {
        this.message = message;
        this.list = list;
        this.status = status;
        this.code = httpStatus.value();
        this.statusCode = httpStatus.name();
        this.exception = exception;

    }
}

