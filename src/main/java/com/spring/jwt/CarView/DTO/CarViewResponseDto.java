package com.spring.jwt.CarView.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarViewResponseDto {

    private String message;
    private String status;
    private int code;
    private String statusCode;
    private String exception;

    public CarViewResponseDto(String message,
                               String status,
                               HttpStatus httpStatus,
                               String exception)
    {
        this.message = message;
        this.status = status;
        this.code = httpStatus.value();
        this.statusCode = httpStatus.name();
        this.exception = exception;
    }
}
