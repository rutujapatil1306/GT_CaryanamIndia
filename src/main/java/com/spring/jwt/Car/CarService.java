package com.spring.jwt.Car;

import com.spring.jwt.Car.DTO.CarDto;
import com.spring.jwt.Car.DTO.CarResponseDto;

import java.util.List;

public interface CarService {

    // Add Car
    CarDto addCar(CarDto cardto);

    //Get Car By id
    CarDto getCarById(int id);

    //Update Car By Given id
    CarDto updateCar(CarDto carDto, int id);

    //Delete Car By id (Soft Delete)
    void softDelete(int id);

    //Delete Car By id (Hard Delete)
    void hardDelete(int id);

    //Get All Cars By Status
    CarResponseDto<List<CarDto>> getAllCarsByStatus(String carStatus, int page, int size);

    //Get Cars By DealerId and Status
    CarResponseDto<List<CarDto>> getCarsByDealerIdAndStatus(Integer dealerId, String carStatus, int page, int size);

    //Get Number Of Cars by dealer Id And Status
    long getNumberOfCarsByDealerIdAndStatus(Integer dealerId, String carStatus);

    //Get Car By MainCarID
    CarDto getCarByMainCarId(String mainCarId);


    CarResponseDto<List<CarDto>> getCarsWithPaginationOnlyActivePending(int page, int size);
    CarResponseDto<List<CarDto>> getCarsWithoutPaginationOnlyActivePending();



}
