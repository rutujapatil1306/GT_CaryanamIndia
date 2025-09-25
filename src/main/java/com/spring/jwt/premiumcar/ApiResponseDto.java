package com.spring.jwt.premiumcar;

public class ApiResponseDto {

    private String status;
    private String message;
    private int code;
    private String statusCode;
    private String exception;

    public ApiResponseDto() {}

    public ApiResponseDto(String status, String message, int code, String statusCode, String exception) {
        this.status = status;
        this.message = message;
        this.code = code;
        this.statusCode = statusCode;
        this.exception = exception;
    }

    // Getters & Setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    public String getStatusCode() { return statusCode; }
    public void setStatusCode(String statusCode) { this.statusCode = statusCode; }

    public String getException() { return exception; }
    public void setException(String exception) { this.exception = exception; }
}

