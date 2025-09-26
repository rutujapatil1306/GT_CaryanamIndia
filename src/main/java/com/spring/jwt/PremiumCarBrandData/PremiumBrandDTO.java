package com.spring.jwt.PremiumCarBrandData;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PremiumBrandDTO {
    private Integer premiumCarBrandDataId;
    @NotBlank(message = "Brand is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Brand must contain only letters")
    private String brand;

    @NotBlank(message = "Variant is required")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])[A-Za-z0-9 ]+$",
            message = "Variant must contain letters and may include digits, but not only digits"
    )
    private String variant;

    @NotBlank(message = "Sub-variant is required")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])[A-Za-z0-9 ]+$",
            message = "Sub-variant must contain letters and may include digits, but not only digits"
    )
    private String subVariant;
}
