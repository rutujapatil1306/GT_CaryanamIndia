package com.spring.jwt.BrandData.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BrandDataDto {

    private Integer brandDataId;

    private String brand;

    private String variant;

    private String subVariant;


}
