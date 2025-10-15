package com.spring.jwt.pendingbooking.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendingBookingResponseDto2<T> {

    private String message;
    private T list;
    private String status;
    private int code;
    private String statusCode;
    private String exception;


    public PendingBookingResponseDto2(String message,
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
