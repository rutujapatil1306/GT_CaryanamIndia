package com.spring.jwt.PremiumCarData;

import java.util.List;
import java.util.stream.Collectors;

public class PremiumCarMapper {

    //  Entity → DTO
    public static PremiumCarDTO toDto(PremiumCar entity) {
        if (entity == null) {
            return null;
        }

        PremiumCarDTO dto = new PremiumCarDTO();
        dto.setPremiumCarId(entity.getPremiumCarId());
        dto.setBrand(entity.getBrand());
        dto.setMainCarId(entity.getMainCarId());
        dto.setDealerId(entity.getDealerId());
        dto.setPrice(entity.getPrice());
        dto.setTitle(entity.getTitle());
        dto.setCarStatus(entity.getCarStatus());

        dto.setAirbag(entity.getAirbag());
        dto.setABS(entity.getABS());
        dto.setButtonStart(entity.getButtonStart());
        dto.setSunroof(entity.getSunroof());
        dto.setChildSafetyLocks(entity.getChildSafetyLocks());
        dto.setAcFeature(entity.getAcFeature());
        dto.setMusicFeature(entity.getMusicFeature());
        dto.setArea(entity.getArea());
        dto.setVariant(entity.getVariant());
        dto.setCarInsurance(entity.getCarInsurance());
        dto.setCarInsuranceDate(entity.getCarInsuranceDate());
        dto.setCarInsuranceType(entity.getCarInsuranceType());
        dto.setPendingApproval(entity.isPendingApproval());
        dto.setCity(entity.getCity());
        dto.setColor(entity.getColor());
        dto.setDescription(entity.getDescription());
        dto.setFuelType(entity.getFuelType());
        dto.setKmDriven(entity.getKmDriven());
        dto.setModel(entity.getModel());
        dto.setOwnerSerial(entity.getOwnerSerial());
        dto.setPowerWindowFeature(entity.getPowerWindowFeature());
        dto.setRearParkingCameraFeature(entity.getRearParkingCameraFeature());
        dto.setRegistration(entity.getRegistration());
        dto.setTransmission(entity.getTransmission());
        dto.setYear(entity.getYear());
        dto.setDate(entity.getDate());
        dto.setCarPhotoId(entity.getCarPhotoId());

        return dto;
    }

    //  DTO → Entity
    public static PremiumCar toEntity(PremiumCarDTO dto) {
        if (dto == null) {
            return null;
        }

        PremiumCar entity = new PremiumCar();
        entity.setPremiumCarId(dto.getPremiumCarId());
        entity.setBrand(dto.getBrand());
        entity.setMainCarId(dto.getMainCarId());
        entity.setDealerId(dto.getDealerId());
        entity.setPrice(dto.getPrice());
        entity.setTitle(dto.getTitle());
        entity.setCarStatus(dto.getCarStatus());

        entity.setAirbag(dto.getAirbag());
        entity.setABS(dto.getABS());
        entity.setButtonStart(dto.getButtonStart());
        entity.setSunroof(dto.getSunroof());
        entity.setChildSafetyLocks(dto.getChildSafetyLocks());
        entity.setAcFeature(dto.getAcFeature());
        entity.setMusicFeature(dto.getMusicFeature());
        entity.setArea(dto.getArea());
        entity.setVariant(dto.getVariant());
        entity.setCarInsurance(dto.getCarInsurance());
        entity.setCarInsuranceDate(dto.getCarInsuranceDate());
        entity.setCarInsuranceType(dto.getCarInsuranceType());
        entity.setPendingApproval(dto.getPendingApproval() != null ? dto.getPendingApproval() : false);
        entity.setCity(dto.getCity());
        entity.setColor(dto.getColor());
        entity.setDescription(dto.getDescription());
        entity.setFuelType(dto.getFuelType());
        entity.setKmDriven(dto.getKmDriven());
        entity.setModel(dto.getModel());
        entity.setOwnerSerial(dto.getOwnerSerial());
        entity.setPowerWindowFeature(dto.getPowerWindowFeature());
        entity.setRearParkingCameraFeature(dto.getRearParkingCameraFeature());
        entity.setRegistration(dto.getRegistration());
        entity.setTransmission(dto.getTransmission());
        entity.setYear(dto.getYear());
        entity.setDate(dto.getDate());
        entity.setCarPhotoId(dto.getCarPhotoId());

        return entity;
    }

    // Partial Update (PATCH)
    public static void updateEntityFromDto(PremiumCarDTO dto, PremiumCar entity) {
        if (dto == null || entity == null) {
            return;
        }

        if (dto.getBrand() != null) entity.setBrand(dto.getBrand());
        if (dto.getMainCarId() != null) entity.setMainCarId(dto.getMainCarId());
        if (dto.getDealerId() != null) entity.setDealerId(dto.getDealerId());
        if (dto.getPrice() != null) entity.setPrice(dto.getPrice());
        if (dto.getTitle() != null) entity.setTitle(dto.getTitle());
        if (dto.getCarStatus() != null) entity.setCarStatus(dto.getCarStatus());

        if (dto.getAirbag() != null) entity.setAirbag(dto.getAirbag());
        if (dto.getABS() != null) entity.setABS(dto.getABS());
        if (dto.getButtonStart() != null) entity.setButtonStart(dto.getButtonStart());
        if (dto.getSunroof() != null) entity.setSunroof(dto.getSunroof());
        if (dto.getChildSafetyLocks() != null) entity.setChildSafetyLocks(dto.getChildSafetyLocks());
        if (dto.getAcFeature() != null) entity.setAcFeature(dto.getAcFeature());
        if (dto.getMusicFeature() != null) entity.setMusicFeature(dto.getMusicFeature());
        if (dto.getArea() != null) entity.setArea(dto.getArea());
        if (dto.getVariant() != null) entity.setVariant(dto.getVariant());
        if (dto.getCarInsurance() != null) entity.setCarInsurance(dto.getCarInsurance());
        if (dto.getCarInsuranceDate() != null) entity.setCarInsuranceDate(dto.getCarInsuranceDate());
        if (dto.getCarInsuranceType() != null) entity.setCarInsuranceType(dto.getCarInsuranceType());
        if (dto.getPendingApproval() != null) entity.setPendingApproval(dto.getPendingApproval());
        if (dto.getCity() != null) entity.setCity(dto.getCity());
        if (dto.getColor() != null) entity.setColor(dto.getColor());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
        if (dto.getFuelType() != null) entity.setFuelType(dto.getFuelType());
        if (dto.getKmDriven() != null) entity.setKmDriven(dto.getKmDriven());
        if (dto.getModel() != null) entity.setModel(dto.getModel());
        if (dto.getOwnerSerial() != null) entity.setOwnerSerial(dto.getOwnerSerial());
        if (dto.getPowerWindowFeature() != null) entity.setPowerWindowFeature(dto.getPowerWindowFeature());
        if (dto.getRearParkingCameraFeature() != null) entity.setRearParkingCameraFeature(dto.getRearParkingCameraFeature());
        if (dto.getRegistration() != null) entity.setRegistration(dto.getRegistration());
        if (dto.getTransmission() != null) entity.setTransmission(dto.getTransmission());
        if (dto.getYear() != null) entity.setYear(dto.getYear());
        if (dto.getDate() != null) entity.setDate(dto.getDate());
        if (dto.getCarPhotoId() != null) entity.setCarPhotoId(dto.getCarPhotoId());
    }

    //  List<Entity> → List<DTO>
    public static List<PremiumCarDTO> toDtoList(List<PremiumCar> entities) {
        return entities.stream()
                .map(PremiumCarMapper::toDto)
                .collect(Collectors.toList());
    }

    //  List<DTO> → List<Entity>
    public static List<PremiumCar> toEntityList(List<PremiumCarDTO> dtos) {
        return dtos.stream()
                .map(PremiumCarMapper::toEntity)
                .collect(Collectors.toList());
    }
}
