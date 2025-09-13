package com.spring.jwt.Car;

import com.spring.jwt.Car.DTO.CarDto;
import com.spring.jwt.Car.DTO.CarResponseDto;
import com.spring.jwt.Car.DTO.UpdateCarResponseDto;
import com.spring.jwt.utils.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    CarService carService;

    @Autowired
    CarRepository carRepository;

    //Add Car
    @PostMapping
    public ResponseEntity<ResponseDto> createCar(@RequestBody CarDto carDto) {
        CarDto addedCar = carService.addCar(carDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto("success", "Car Added Successfully"));
    }

    // Get Car By Id
    //Using RequestParam to take car id
    @GetMapping
    public ResponseEntity<CarResponseDto> getCarById(@RequestParam int id) {
        CarDto car = carService.getCarById(id);
        long totalNoOfCars = carRepository.count();
        return ResponseEntity.ok(CarResponseDto.success("Fetched Car with given id " + id + " is Found", car, totalNoOfCars));
    }

    //Update Car By Id
    @PatchMapping("/update")
    public ResponseEntity<UpdateCarResponseDto> updateCar(@RequestBody CarDto carDto, @RequestParam int id) {
        CarDto updateCar = carService.updateCar(carDto, id);
        return ResponseEntity.ok(new UpdateCarResponseDto<>("Car Updated Successfully", updateCar, null));
    }

    // Delete Car(Soft Delete)
    @DeleteMapping("/softDelete")
    public ResponseEntity<ResponseDto> softDelete(@RequestParam int id) {
        carService.softDelete(id);
        return ResponseEntity.ok(new ResponseDto("Success", "Car Deleted Successfully"));
    }

    //Delete Car(Hard Delete)
    @DeleteMapping("/hardDelete")
    public ResponseEntity<ResponseDto> hardDelete(@RequestParam int id) {
        carService.hardDelete(id);
        return ResponseEntity.ok(new ResponseDto("Success", "Car Deleted Successfully"));
    }

    //Get Car By Status
    @GetMapping("/status")
    public ResponseEntity<CarResponseDto<List<CarDto>>> getAllCarsByStatus(@RequestParam String carStatus,
                                                                           @RequestParam int page,
                                                                           @RequestParam int size)
    {
        CarResponseDto<List<CarDto>> response = carService.getAllCarsByStatus(carStatus, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dealer")
    public ResponseEntity<CarResponseDto<List<CarDto>>> getCarsByDealerIdAndCarStatus(@RequestParam Integer dealerId,
                                                                                      @RequestParam String carStatus,
                                                                                      @RequestParam int page,
                                                                                      @RequestParam int size)
    {
        CarResponseDto<List<CarDto>> response = carService.getCarsByDealerIdAndStatus(dealerId, carStatus, page, size);
        return ResponseEntity.ok(response);
    }
}