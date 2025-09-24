package com.spring.jwt.premiumcar;

import com.spring.jwt.entity.Car;
import com.spring.jwt.premiumcar.PremiumCarPhoto;
import com.spring.jwt.premiumcar.PremiumCarPhotoDto;

public class PremiumCarPhotoMapper {

    public static PremiumCarPhotoDto toDto(PremiumCarPhoto photo) {
        PremiumCarPhotoDto dto = new PremiumCarPhotoDto();
        dto.setId(photo.getPhotoId());
        dto.setPremiumCarId(photo.getPremiumCar().getPremiumCarId());
        dto.setDocType(photo.getDocType().name());
        dto.setFileSize(photo.getFileSize());
        dto.setContentType(photo.getContentType());
        dto.setFileUrl(photo.getFileUrl());
        dto.setUploadedAt(photo.getUploadedAt());
        return dto;
    }
}

