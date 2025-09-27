package com.spring.jwt.CarView.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CarViewDto {

    private Integer id;

    private Integer user_id;

    private Integer car_id;

    private Integer count;

    private LocalDateTime lastViewedAt;

}
