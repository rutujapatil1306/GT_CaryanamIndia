package com.spring.jwt.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class BaseException extends RuntimeException{

    private String code;

    private String message;

    //  Add this constructor below the existing ones
    public BaseException(String message) {
        super(message);
        this.code = "500"; // default code
        this.message = message;
    }


}
