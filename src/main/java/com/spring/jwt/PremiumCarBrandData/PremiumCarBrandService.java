package com.spring.jwt.PremiumCarBrandData;

import com.spring.jwt.dto.ResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PremiumCarBrandService {
    PremiumBrandDTO save(PremiumBrandDTO dto);
    PremiumBrandDTO updateBrandById(PremiumBrandDTO brandDataDto, Integer premiumCarBrandDataId);
    PremiumBrandDTO deletePremiumBrand(Integer premiumCarBrandDataId);
    PremiumBrandDTO getPremiumBrandById(Integer premiumCarBrandDataId);
    List<PremiumBrandDTO> getAllPremiumBrands(int page, int size);
    List<String> getUniqueBrands();
    List<String> getVariantsByBrand(String brand);
    List<String> getSubVariantsByBrandAndVariant(String brand, String variant);

}
