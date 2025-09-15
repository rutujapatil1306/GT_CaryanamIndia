package com.spring.jwt.PremiumCarData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PremiunCarCountDto {
    private Integer dealerId;
    private String carStatus;
    private long count;
}
