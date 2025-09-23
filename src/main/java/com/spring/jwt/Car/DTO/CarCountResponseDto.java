package com.spring.jwt.Car.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarCountResponseDto {

    private Integer dealerId;
    private String carStatus;
    private long count;
}
