package com.spring.jwt.BrandData;

import com.spring.jwt.BrandData.DTO.BrandDataDto;

import java.util.List;

public interface BrandDataService {
    BrandDataDto createBrand(BrandDataDto brandDataDto);

    BrandDataDto getBrandById(Integer brandDataId);

    BrandDataDto updateBrandById(BrandDataDto brandDataDto, Integer brandDataId);

    void deleteBrand(Integer brandDataId);

    List<BrandDataDto> getAllBrands(int page, int size);

    List<String> getUniqueBrands();

    List<String> getVariantsByBrand(String brand);

    List<String> getSubVariantsByBrandVariant(String brand, String variant);


}
