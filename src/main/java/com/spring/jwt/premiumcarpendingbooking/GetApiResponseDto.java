package com.spring.jwt.premiumcarpendingbooking;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetApiResponseDto {
    private String status;
    private String message;
    private Object data;
}
