package com.spring.jwt.PremiumCarData;

import com.spring.jwt.entity.Dealer;

public class PremiumCarMapper {
    public static PremiumCar toEntity(PremiumCarDTO dto) {

        PremiumCar entity = new PremiumCar();
        entity.setPremiumCarId(dto.getPremiumCarId());
        entity.setAirbag(dto.getAirbag());
        entity.setABS(dto.getABS());
        entity.setButtonStart(dto.getButtonStart());
        entity.setSunroof(dto.getSunroof());
        entity.setChildSafetyLocks(dto.getChildSafetyLocks());
        entity.setAcFeature(dto.getAcFeature());
        entity.setMusicFeature(dto.getMusicFeature());
        entity.setArea(dto.getArea());
        entity.setVariant(dto.getVariant());
        entity.setBrand(dto.getBrand());
        entity.setCarInsurance(dto.getCarInsurance());
        entity.setCarInsuranceDate(dto.getCarInsuranceDate());
        entity.setCarInsuranceType(dto.getCarInsuranceType());
        entity.setCarstatus(dto.getCarstatus());
        entity.setPendingApproval(dto.isPendingApproval());
        entity.setCity(dto.getCity());
        entity.setColor(dto.getColor());
        entity.setDescription(dto.getDescription());
        entity.setFuelType(dto.getFuelType());
        entity.setKmDriven(dto.getKmDriven());
        entity.setModel(dto.getModel());
        entity.setOwnerSerial(dto.getOwnerSerial());
        entity.setPowerWindowFeature(dto.getPowerWindowFeature());
        entity.setPrice(dto.getPrice());
        entity.setRearParkingCameraFeature(dto.getRearParkingCameraFeature());
        entity.setRegistration(dto.getRegistration());
        entity.setTitle(dto.getTitle());
        entity.setTransmission(dto.getTransmission());
        entity.setYear(dto.getYear());
        entity.setDate(dto.getDate());
//        entity.setDealerId(dto.getDealerId());
        entity.setCarPhotoId(dto.getCarPhotoId());
        entity.setMainCarId(dto.getMainCarId());
        entity.setCarType(dto.getCarType());
        if (dto.getDealerId() != null) {
            Dealer dealer = new Dealer();
            dealer.setId(dto.getDealerId());
            entity.setDealer(dealer);
        } else {
            entity.setDealer(null);
        }
        return entity;
    }

    // Entity → DTO
    public static PremiumCarDTO toDTO(PremiumCar entity) {
        if (entity == null) return null;


        return PremiumCarDTO.builder()
                .premiumCarId(entity.getPremiumCarId())
                .airbag(entity.getAirbag())
                .ABS(entity.getABS())
                .buttonStart(entity.getButtonStart())
                .sunroof(entity.getSunroof())
                .childSafetyLocks(entity.getChildSafetyLocks())
                .acFeature(entity.getAcFeature())
                .musicFeature(entity.getMusicFeature())
                .area(entity.getArea())
                .variant(entity.getVariant())
                .brand(entity.getBrand())
                .carInsurance(entity.getCarInsurance())
                .carInsuranceDate(entity.getCarInsuranceDate())
                .carInsuranceType(entity.getCarInsuranceType())
                .carstatus(entity.getCarstatus())
                .pendingApproval(entity.isPendingApproval())
                .city(entity.getCity())
                .color(entity.getColor())
                .description(entity.getDescription())
                .fuelType(entity.getFuelType())
                .kmDriven(entity.getKmDriven())
                .model(entity.getModel())
                .ownerSerial(entity.getOwnerSerial())
                .powerWindowFeature(entity.getPowerWindowFeature())
                .price(entity.getPrice())
                .rearParkingCameraFeature(entity.getRearParkingCameraFeature())
                .registration(entity.getRegistration())
                .title(entity.getTitle())
                .transmission(entity.getTransmission())
                .year(entity.getYear())
                .date(entity.getDate())
                .dealerId(entity.getDealer() != null ? entity.getDealer().getId() : null)
                .carPhotoId(entity.getCarPhotoId())
                .mainCarId(entity.getMainCarId())
                .carType(entity.getCarType())
                .build();

    }
    }

