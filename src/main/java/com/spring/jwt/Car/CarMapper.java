package com.spring.jwt.Car;

import com.spring.jwt.Car.DTO.CarDto;
import com.spring.jwt.entity.Car;
import com.spring.jwt.entity.Dealer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CarMapper {

    public CarDto toDto(Car car)
    {
        if(car == null)
        {
            return null;
        }

        try
        {
            CarDto dto = new CarDto();

            dto.setId(car.getId());
            dto.setAbs(car.getAbs());
            dto.setArea(car.getArea());
            dto.setBrand(car.getBrand());
            dto.setAirbag(car.getAirbag());
            dto.setCarInsurance(car.getCarInsurance());
            dto.setAcFeature(car.getAcFeature());
            dto.setButtonStart(car.getButtonStart());
            dto.setCity(car.getCity());
            dto.setDate(car.getDate());
            dto.setCarPhotoId(car.getCarPhotoId());
            dto.setCarInsuranceDate(car.getCarInsuranceDate());
            dto.setCarType(car.getCarType());
            dto.setChildSafetyLocks(car.getChildSafetyLocks());
            dto.setCarInsuranceType(car.getCarInsuranceType());
            dto.setColor(car.getColor());
            //dto.setDealerId(car.getDealerId());
            dto.setDescription(car.getDescription());
            dto.setFuelType(car.getFuelType());
            dto.setKmDriven(car.getKmDriven());
            dto.setMainCarId(car.getMainCarId());
            dto.setModel(car.getModel());
            dto.setMusicFeature(car.getMusicFeature());
            dto.setOwnerSerial(car.getOwnerSerial());
            dto.setPendingApproval(car.getPendingApproval());
            dto.setPowerWindowFeature(car.getPowerWindowFeature());
            dto.setPrice(car.getPrice());
            dto.setRearParkingCameraFeature(car.getRearParkingCameraFeature());
            dto.setRegistration(car.getRegistration());
            dto.setSunroof(car.getSunroof());
            dto.setTitle(car.getTitle());
            dto.setTransmission(car.getTransmission());
            dto.setVariant(car.getVariant());
            dto.setYear(car.getYear());
            dto.setCarStatus(car.getCarStatus());
            if (car.getDealer() != null) {
                dto.setDealerId(car.getDealer().getId());
            }
            log.debug("Mapped Car To DTO: {}", dto);
            return dto;

        }
        catch(Exception ex)
        {
            log.error("Error Converting Car To Dto: {}", ex.getMessage(),ex);
            throw new RuntimeException("Error converting Car to DTO", ex);
        }
    }

    public Car toEntity(CarDto carDto)
    {
        if(carDto == null)
        {
            return null;
        }

        try
        {
            Car car = new Car();

            car.setId(carDto.getId());
            car.setAbs(carDto.getAbs());
            car.setArea(carDto.getArea());
            car.setBrand(carDto.getBrand());
            car.setAirbag(carDto.getAirbag());
            car.setCarInsurance(carDto.getCarInsurance());
            car.setAcFeature(carDto.getAcFeature());
            car.setButtonStart(carDto.getButtonStart());
            car.setCity(carDto.getCity());
            car.setDate(carDto.getDate());
            car.setCarPhotoId(carDto.getCarPhotoId());
            car.setCarInsuranceDate(carDto.getCarInsuranceDate());
            car.setCarType(carDto.getCarType());
            car.setChildSafetyLocks(carDto.getChildSafetyLocks());
            car.setCarInsuranceType(carDto.getCarInsuranceType());
            car.setColor(carDto.getColor());
            //car.setDealerId(carDto.getDealerId());
            car.setDescription(carDto.getDescription());
            car.setFuelType(carDto.getFuelType());
            car.setKmDriven(carDto.getKmDriven());
            car.setMainCarId(carDto.getMainCarId());
            car.setModel(carDto.getModel());
            car.setMusicFeature(carDto.getMusicFeature());
            car.setOwnerSerial(carDto.getOwnerSerial());
            car.setPendingApproval(carDto.getPendingApproval());

            car.setPowerWindowFeature(carDto.getPowerWindowFeature());
            car.setPrice(carDto.getPrice());
            car.setRearParkingCameraFeature(carDto.getRearParkingCameraFeature());
            car.setRegistration(carDto.getRegistration());
            car.setSunroof(carDto.getSunroof());
            car.setTitle(carDto.getTitle());
            car.setTransmission(carDto.getTransmission());
            car.setVariant(carDto.getVariant());
            car.setYear(carDto.getYear());
            car.setCarStatus(carDto.getCarStatus());

            return car;
        } catch (Exception ex) {
            log.error("Error Converting Dto to Car: {}", ex.getMessage(),ex);
            throw new RuntimeException("Error converting Dto to Car", ex);
        }
    }

    public Car updateCarFromDto(Car car, CarDto carDto)
    {
        //Update Fields


        if (carDto.getAirbag() != null) car.setAirbag(carDto.getAirbag());
        if (carDto.getAbs() != null) car.setAbs(carDto.getAbs());
        if (carDto.getButtonStart() != null) car.setButtonStart(carDto.getButtonStart());
        if (carDto.getSunroof() != null) car.setSunroof(carDto.getSunroof());
        if (carDto.getChildSafetyLocks() != null) car.setChildSafetyLocks(carDto.getChildSafetyLocks());
        if (carDto.getAcFeature() != null) car.setAcFeature(carDto.getAcFeature());
        if (carDto.getMusicFeature() != null) car.setMusicFeature(carDto.getMusicFeature());
        if (carDto.getArea() != null) car.setArea(carDto.getArea());
        if (carDto.getVariant() != null) car.setVariant(carDto.getVariant());
        if (carDto.getBrand() != null) car.setBrand(carDto.getBrand());
        if (carDto.getCarInsurance() != null) car.setCarInsurance(carDto.getCarInsurance());
        if (carDto.getCarInsuranceDate() != null) car.setCarInsuranceDate(carDto.getCarInsuranceDate());
        if (carDto.getCarInsuranceType() != null) car.setCarInsuranceType(carDto.getCarInsuranceType());
        if (carDto.getCarStatus() != null) car.setCarStatus(carDto.getCarStatus());

        if (carDto.getPendingApproval() != null) car.setPendingApproval(carDto.getPendingApproval());

        if (carDto.getCity() != null) car.setCity(carDto.getCity());
        if (carDto.getColor() != null) car.setColor(carDto.getColor());
        if (carDto.getDescription() != null) car.setDescription(carDto.getDescription());
        if (carDto.getFuelType() != null) car.setFuelType(carDto.getFuelType());
        if (carDto.getKmDriven() != null) car.setKmDriven(carDto.getKmDriven());
        if (carDto.getModel() != null) car.setModel(carDto.getModel());
        if (carDto.getOwnerSerial() != null) car.setOwnerSerial(carDto.getOwnerSerial());
        if (carDto.getPowerWindowFeature() != null) car.setPowerWindowFeature(carDto.getPowerWindowFeature());
        if (carDto.getPrice() != null) car.setPrice(carDto.getPrice());
        if (carDto.getRearParkingCameraFeature() != null) car.setRearParkingCameraFeature(carDto.getRearParkingCameraFeature());
        if (carDto.getRegistration() != null) car.setRegistration(carDto.getRegistration());
        if (carDto.getTitle() != null) car.setTitle(carDto.getTitle());
        if (carDto.getTransmission() != null) car.setTransmission(carDto.getTransmission());
        if (carDto.getYear() != null) car.setYear(carDto.getYear());
        if (carDto.getDate() != null) car.setDate(carDto.getDate());
        if (carDto.getCarPhotoId() != null) car.setCarPhotoId(carDto.getCarPhotoId());
        if (carDto.getMainCarId() != null) car.setMainCarId(carDto.getMainCarId());
        if (carDto.getCarType() != null) car.setCarType(carDto.getCarType());



        return car;
    }


}
