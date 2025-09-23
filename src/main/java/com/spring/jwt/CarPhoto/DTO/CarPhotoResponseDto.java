package com.spring.jwt.CarPhoto.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarPhotoResponseDto {

    private String message;
    private String status;
    private int code;
    private String statusCode;
    private String exception;

    public CarPhotoResponseDto(String message,
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
