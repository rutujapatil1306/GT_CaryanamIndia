package com.spring.jwt.Car;

import com.spring.jwt.Car.DTO.CarDto;
import com.spring.jwt.entity.Car;
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
            dto.setDealerId(car.getDealerId());
            dto.setDescription(car.getDescription());
            dto.setFuelType(car.getFuelType());
            dto.setKmDriven(car.getKmDriven());
            dto.setMainCarId(car.getMainCarId());
            dto.setModel(car.getModel());
            dto.setMusicFeature(car.getMusicFeature());
            dto.setOwnerSerial(car.getOwnerSerial());
            dto.setPendingApproval(car.isPendingApproval());
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
            car.setDealerId(carDto.getDealerId());
            car.setDescription(carDto.getDescription());
            car.setFuelType(carDto.getFuelType());
            car.setKmDriven(carDto.getKmDriven());
            car.setMainCarId(carDto.getMainCarId());
            car.setModel(carDto.getModel());
            car.setMusicFeature(carDto.getMusicFeature());
            car.setOwnerSerial(carDto.getOwnerSerial());
            car.setPendingApproval(carDto.isPendingApproval());
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


}
