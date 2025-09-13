package com.spring.jwt.PremiumCarBrandData;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PremiumBrandDTO {
    private Integer premiumCarBrandDataId;

    @NotBlank(message = "Brand name cannot be blank")
    private String brand;

    @NotBlank(message = "Variant cannot be blank")
    private String variant;

    @NotBlank(message = "Sub-variant cannot be blank")
    private String subVariant;
}
