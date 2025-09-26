package com.spring.jwt.BrandData;

import com.spring.jwt.BrandData.DTO.BrandDataDto;
import com.spring.jwt.BrandData.Exception.BrandAlreadyExistsException;
import com.spring.jwt.BrandData.Exception.BrandNotFoundException;
import com.spring.jwt.BrandData.Exception.SubVariantNotFoundException;
import com.spring.jwt.BrandData.Exception.VariantNotFoundException;
import com.spring.jwt.entity.BrandData;
import com.spring.jwt.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandDataServiceImpl implements BrandDataService {

    @Autowired
    BrandDataRepository brandDataRepository;

    @Autowired
    BrandMapper brandMapper;

    //Create Brand

    @Override
    public BrandDataDto createBrand(BrandDataDto brandDataDto) {
        try {
            BrandData existing = brandDataRepository.findByBrandAndVariantAndSubVariant(brandDataDto.getBrand(), brandDataDto.getVariant(), brandDataDto.getSubVariant());
            if (existing != null) {
                throw new BrandAlreadyExistsException("BrandData Already Exists");
            }
            BrandData brandData = brandDataRepository.save(brandMapper.toEntity(brandDataDto));

            return brandMapper.toDto(brandData);
        }
        catch(BrandAlreadyExistsException e)
        {
            throw e;
        }
        catch(Exception e){
            throw new RuntimeException("Internal Server Error occurred while creating Brand", e);
        }
    }

    //Get brand By id

    @Override
    public BrandDataDto getBrandById(Integer brandDataId) {
        BrandData brandData = brandDataRepository.findById(brandDataId).orElseThrow(() -> new BrandNotFoundException("Brand not found with id " + brandDataId));


        return brandMapper.toDto(brandData);
    }

    // update brand details
    @Override
    public BrandDataDto updateBrandById(BrandDataDto brandDataDto, Integer brandDataId) {
        BrandData brandData = brandDataRepository.findById(brandDataId).orElseThrow(() -> new ResourceNotFoundException("Brand not found with id" + brandDataId));
        if (brandDataDto.getBrand() != null) {
            brandData.setBrand(brandDataDto.getBrand());
        }
        if (brandDataDto.getVariant() != null) {
            brandData.setVariant(brandDataDto.getVariant());
        }
        if (brandDataDto.getSubVariant() != null) {
            brandData.setSubVariant(brandDataDto.getSubVariant());
        }

        BrandData updatedBrand = brandDataRepository.save(brandData);
        return brandMapper.toDto(updatedBrand);
    }

    //delete brand details

    @Override
    public void deleteBrand(Integer brandDataId) {
        BrandData brandData = brandDataRepository.findById(brandDataId).orElseThrow(() -> new ResourceNotFoundException("Brand not found with id" + brandDataId));
        brandDataRepository.delete(brandData);

    }

    @Override
    public List<BrandDataDto> getAllBrands(int page, int size){

        Pageable pageable = PageRequest.of(page, size, Sort.by("brandDataId").descending());
        Page<BrandData> brandDataPage = brandDataRepository.findAll(pageable);
        if (page >= brandDataPage.getTotalPages()) {
            throw new PageNotFoundException("Page " + page + " not found. Total pages: " + brandDataPage.getTotalPages());
        }
        return brandDataPage.getContent().stream()
                .map(brandData -> brandMapper.toDto(brandData)).toList();
    }

    @Override
    public List<String> getUniqueBrands() {
        List<String> uniqueBrands = brandDataRepository.findDistinctBrands();
        if(uniqueBrands.isEmpty())
        {
            throw new BrandNotFoundException("No Brands Available in Database");
        }
        return uniqueBrands;

    }

    @Override
    public List<String> getVariantsByBrand(String brand) {
        List<BrandData> variants = brandDataRepository.findByBrand(brand);

        if(variants.isEmpty())
        {
            throw new VariantNotFoundException("No Variants found for given brand");
        }

        return variants.stream().filter(b -> b.getBrand().equalsIgnoreCase(brand))
                .map(BrandData::getVariant)
                .filter(v -> v != null  && !v.isBlank())
                .map(String::toLowerCase)
                .distinct()
                .toList();
    }

    @Override
    public List<String> getSubVariantsByBrandVariant(String brand, String variant) {
        List<BrandData> subVariants = brandDataRepository.findByBrandAndVariant(brand, variant);

        if(subVariants.isEmpty())
        {
            throw new SubVariantNotFoundException("No Subvariants found for given brand and variant");
        }

        return subVariants.stream()
                .filter(b -> b.getBrand().equalsIgnoreCase(brand))
                .filter(v -> v.getVariant().equalsIgnoreCase(variant))
                .map(BrandData::getSubVariant)
                .filter(s -> s != null && !s.isBlank())
                .map(String::toLowerCase)
                .distinct()
                .toList();


    }


}
