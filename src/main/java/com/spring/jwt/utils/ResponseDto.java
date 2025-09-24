package com.spring.jwt.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class ResponseDto {
    public String status;
    public String message;

    public ResponseDto(String status, String message) {
        this.status=status;
        this.message=message;
    }
<<<<<<< HEAD
    public ResponseDto() {

    }
=======

>>>>>>> f6478de2863350de09dee9e4d298974975739906
}