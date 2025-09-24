package com.spring.jwt.PremiumCarBrandData;

<<<<<<< HEAD
import com.spring.jwt.dto.ResponseDto;
import com.spring.jwt.exception.PageNotFoundException;
=======
>>>>>>> f6478de2863350de09dee9e4d298974975739906
import com.spring.jwt.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
<<<<<<< HEAD
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
=======
>>>>>>> f6478de2863350de09dee9e4d298974975739906
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PremiumCarBandServiceImpl implements PremiumCarBrandService {
@Autowired
    PremiumCarBrandRepository premiumCarBrandRepository;


    @Override
    public PremiumBrandDTO save(PremiumBrandDTO dto) {
        PremiumCarBrandData entity = PremiumBrandMapper.toEntity(dto);
        return PremiumBrandMapper.toDTO(premiumCarBrandRepository.save(entity));
    }

    @Override
    public PremiumBrandDTO updateBrandById(PremiumBrandDTO brandDataDto, Integer premiumCarBrandDataId) {
        PremiumCarBrandData update = premiumCarBrandRepository.findById(premiumCarBrandDataId).orElseThrow(() -> new ResourceNotFoundException("Brand not found with id" + premiumCarBrandDataId));
        if (brandDataDto.getBrand() != null) {
            update.setBrand(brandDataDto.getBrand());
        }
        if (brandDataDto.getVariant() != null) {
            update.setVariant(brandDataDto.getVariant());
        }
        if (brandDataDto.getSubVariant() != null) {
            update.setSubVariant(brandDataDto.getSubVariant());
        }

        PremiumCarBrandData updatedBrand = premiumCarBrandRepository.save(update);
        return PremiumBrandMapper.toDTO(updatedBrand);
    }




    @Override
    public PremiumBrandDTO deletePremiumBrand(Integer premiumCarBrandDataId) {
        PremiumCarBrandData deleteData = premiumCarBrandRepository.findById(premiumCarBrandDataId)
                .orElseThrow(() -> new ResourceNotFoundException("Premium brand with ID " + premiumCarBrandDataId + " not found"));

        premiumCarBrandRepository.delete(deleteData);
        return PremiumBrandMapper.toDTO(deleteData);
    }
    // fetch Data
    @Override
    public PremiumBrandDTO getPremiumBrandById(Integer premiumCarBrandDataId) {
        PremiumCarBrandData entity = premiumCarBrandRepository.findById(premiumCarBrandDataId)
                .orElseThrow(() -> new PremiumBrandNotFoundException("Brand with ID " + premiumCarBrandDataId + " not found"));
        return PremiumBrandMapper.toDTO(entity);
    }

    @Override
    public List<PremiumBrandDTO> getAllPremiumBrands(int page, int size) {
        Page<PremiumCarBrandData> entities = premiumCarBrandRepository.findAll(PageRequest.of(page, size));
        return entities.stream().map(PremiumBrandMapper::toDTO).collect(Collectors.toList());
    }


    @Override
    public List<String> getUniqueBrands() {
        List<String> brands = premiumCarBrandRepository.findDistinctBrands();
        if (brands.isEmpty()) {
            throw new PremiumBrandNotFoundException("No premium brands found");
        }
        return brands;
    }

    @Override
    public List<String> getVariantsByBrand(String brand) {
        List<String> variants = premiumCarBrandRepository.findVariantsByBrand(brand);
        if (variants.isEmpty()) {
<<<<<<< HEAD
            throw new VariantNotFoundException("No variants found for brand: " + brand);
=======
            throw new VariantNotFoundExceptions("No variants found for brand: " + brand);
>>>>>>> f6478de2863350de09dee9e4d298974975739906
        }
        return variants;
    }

    @Override
    public List<String> getSubVariantsByBrandAndVariant(String brand, String variant) {
        List<String> subVariants = premiumCarBrandRepository.findSubVariantsByBrandAndVariant(brand, variant);
        if (subVariants.isEmpty()) {
<<<<<<< HEAD
            throw new SubVariantNotFoundException("No sub-variants found for brand: " + brand + " and variant: " + variant);
=======
            throw new SubVariantNotFoundExceptions("No sub-variants found for brand: " + brand + " and variant: " + variant);
>>>>>>> f6478de2863350de09dee9e4d298974975739906
        }
        return subVariants;
    }

}
