package com.spring.jwt.PremiumCarBrandData;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "PremiumCarBrandDataEntity", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"brand", "variant", "subVariant"})
})
public class PremiumCarBrandData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "premiumCarBrandDataId")
    private Integer premiumCarBrandDataId;

    @Column(name = "brand")
    private String brand;

    @Column(name = "variant")
    private String variant;

    @Column(name = "subVariant")
    private String subVariant;

    public PremiumCarBrandData() {

    }
}
