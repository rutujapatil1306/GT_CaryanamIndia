package com.spring.jwt.PremiumCarData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PremiumCarResponseDto {

    private String Status;
    private int statusCode;
    private String message;
    private String exception;
    private LocalDateTime timestamp;
//    private T data;


}
