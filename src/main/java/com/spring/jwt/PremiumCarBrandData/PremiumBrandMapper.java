package com.spring.jwt.PremiumCarBrandData;

public class PremiumBrandMapper {
    public static PremiumBrandDTO toDTO(PremiumCarBrandData entity) {
        if (entity == null) return null;
        PremiumBrandDTO dto = new PremiumBrandDTO();
        dto.setPremiumCarBrandDataId(entity.getPremiumCarBrandDataId());
        dto.setBrand(entity.getBrand());
        dto.setVariant(entity.getVariant());
        dto.setSubVariant(entity.getSubVariant());

        return dto;
    }
    //Convert Entity into  Dto
    public static PremiumCarBrandData toEntity(PremiumBrandDTO dto) {
        if (dto == null) return null;
        PremiumCarBrandData entity = new PremiumCarBrandData();
        entity.setPremiumCarBrandDataId(dto.getPremiumCarBrandDataId());
        entity.setBrand(dto.getBrand());
        entity.setVariant(dto.getVariant());
        entity.setSubVariant(dto.getSubVariant());

        return entity;
    }

}
