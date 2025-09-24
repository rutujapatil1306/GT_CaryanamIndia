package com.spring.jwt.PremiumCarBrandData;

import com.spring.jwt.dto.ResponseDto;
<<<<<<< HEAD
=======
import com.spring.jwt.utils.BaseResponseDTO;
import jakarta.validation.Valid;
>>>>>>> f6478de2863350de09dee9e4d298974975739906
import org.springframework.data.domain.Page;

import java.util.List;

public interface PremiumCarBrandService {
<<<<<<< HEAD
    PremiumBrandDTO save(PremiumBrandDTO dto);
=======
   PremiumBrandDTO save(PremiumBrandDTO dto);

>>>>>>> f6478de2863350de09dee9e4d298974975739906
    PremiumBrandDTO updateBrandById(PremiumBrandDTO brandDataDto, Integer premiumCarBrandDataId);
    PremiumBrandDTO deletePremiumBrand(Integer premiumCarBrandDataId);
    PremiumBrandDTO getPremiumBrandById(Integer premiumCarBrandDataId);
    List<PremiumBrandDTO> getAllPremiumBrands(int page, int size);
    List<String> getUniqueBrands();
    List<String> getVariantsByBrand(String brand);
    List<String> getSubVariantsByBrandAndVariant(String brand, String variant);

}
