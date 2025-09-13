package com.spring.jwt.PremiumCarBrandData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PremiumCarBrandRepository extends JpaRepository<PremiumCarBrandData, Integer> {
    @Query("SELECT DISTINCT p.brand FROM PremiumCarBrandData p")
    List<String> findDistinctBrands();

//    @Query("SELECT DISTINCT p.variant FROM PremiumCarBrandData p WHERE p.brand = :brand")
//    List<String> findVariantsByBrand(String brand);

    @Query("SELECT DISTINCT p.variant FROM PremiumCarBrandData p WHERE p.brand = :brand")
    List<String> findVariantsByBrand(@Param("brand") String brand);

    @Query("SELECT DISTINCT p.subVariant FROM PremiumCarBrandData p WHERE p.brand = :brand AND p.variant = :variant")
    List<String> findSubVariantsByBrandAndVariant(String brand, String variant);
}
