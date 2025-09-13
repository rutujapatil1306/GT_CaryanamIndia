package com.spring.jwt.BrandData;

import com.spring.jwt.BrandData.DTO.BrandDataDto;
import com.spring.jwt.entity.BrandData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BrandMapper {

    public BrandDataDto toDto(BrandData brandData)
    {
        if(brandData == null)
        {
            return null;
        }

        try
        {
            BrandDataDto brandDataDto = new BrandDataDto();
            brandDataDto.setBrandDataId(brandData.getBrandDataId());
            brandDataDto.setBrand(brandData.getBrand());
            brandDataDto.setVariant(brandData.getVariant());
            brandDataDto.setSubVariant(brandData.getSubVariant());

            log.debug("Mapped brand Data to DTO - brand: {}, variant: {}, sub-variant: {}",
                    brandDataDto.getBrand(), brandDataDto.getVariant(), brandDataDto.getSubVariant());
            return brandDataDto;
        }
        catch(Exception ex)
        {
            log.error("Error Converting BrandData To Dto: {}", ex.getMessage(),ex);
            throw new RuntimeException("Error converting BrandData to DTO", ex);
        }

    }

    public BrandData toEntity(BrandDataDto brandDataDto)
    {
        if(brandDataDto == null)
        {
            return null;
        }

        try
        {
            BrandData brandData = new BrandData();
            brandData.setBrandDataId(brandDataDto.getBrandDataId());
            brandData.setBrand(brandDataDto.getBrand());
            brandData.setVariant(brandDataDto.getVariant());
            brandData.setSubVariant(brandDataDto.getSubVariant());

            log.debug("Mapped Dto to Brand Data - brand: {}, variant: {}, subVariant: {}",
                    brandData.getBrand(), brandData.getVariant(), brandData.getSubVariant());
            return  brandData;
        }
        catch(Exception ex)
        {
            log.error("Error Converting Dto to BrandData: {}", ex.getMessage(),ex);
            throw new RuntimeException("Error converting Dto to Brand Data", ex);
        }
    }
}
