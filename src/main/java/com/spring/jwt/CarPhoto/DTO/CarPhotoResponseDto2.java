package com.spring.jwt.CarPhoto.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarPhotoResponseDto2<T> {


    private String message;
    private T list;
    private String status;
    private int code;
    private String statusCode;
    private String exception;


    public CarPhotoResponseDto2(String message,
                                T list,
                               String status,
                               HttpStatus httpStatus,
                               String exception
                               )
    {
        this.message = message;
        this.list = list;
        this.status = status;
        this.code = httpStatus.value();
        this.statusCode = httpStatus.name();
        this.exception = exception;

    }
}
