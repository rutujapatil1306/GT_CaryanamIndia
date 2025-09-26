package com.spring.jwt.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@Table(name = "BrandData", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"brand", "variant", "subVariant"})
})
public class BrandData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brandDataId", nullable = false)
    private Integer brandDataId;

    @NotBlank(message = "Brand name cannot be empty")
    @Pattern(regexp = "^[A-Za-z ]{1,49}$", message = "Brand must contain only letters, spaces")
    @Column(name = "brand")
    private String brand;

    @NotBlank(message = "Variant name cannot be empty")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9\\s-]{0,49}$", message = "Variant must contain only letters, numbers, spaces")
    @Column(name = "variant")
    private String variant;

    @NotBlank(message = "SubVariant name cannot be empty")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9\\s.-]{0,49}$", message = "SubVariant must contain only letters, numbers, spaces")


    @NotBlank(message = "SubVariant name cannot be empty")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9\\s.-]{0,49}$", message = "SubVariant must contain only letters, numbers, spaces")
    @Column(name = "subVariant")
    private String subVariant;


    public BrandData() {

    }
}
