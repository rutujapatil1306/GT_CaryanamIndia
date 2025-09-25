package com.spring.jwt.PremiumCarBrandData;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data

@NoArgsConstructor
public class PremiumBrandResponseDto <T>{

    private String Status;
    private int statusCode;
    private String message;
    private String exception;
    private LocalDateTime timestamp;
//    private T data;



    public  PremiumBrandResponseDto(String Status, int StatusCode, String message, String exception, LocalDateTime timestamp) {
        this.Status = Status;
        this.statusCode = statusCode;
        this.message = message;
        this.exception = exception;
        this.timestamp = timestamp;
    }

}
