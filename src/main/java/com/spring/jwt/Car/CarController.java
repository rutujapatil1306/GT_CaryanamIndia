package com.spring.jwt.Car;

import com.spring.jwt.Car.DTO.CarCountResponseDto;
import com.spring.jwt.Car.DTO.CarDto;
import com.spring.jwt.Car.DTO.CarResponseDto;
import com.spring.jwt.Car.DTO.CarResponseDto2;
import com.spring.jwt.utils.ResponseDto;
import jakarta.validation.Valid;
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
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCar(@Valid @RequestBody CarDto carDto) {
        CarDto addedCar = carService.addCar(carDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto("success", "Car Added Successfully"));
    }

    // Get Car By Id
    //Using RequestParam to take car id
    @GetMapping("/getCar")
    public ResponseEntity<CarResponseDto2> getCarById(@RequestParam Integer userId,
                                                      @RequestParam Integer id) {
        CarDto car = carService.getCarById(userId, id);
        return ResponseEntity.ok(CarResponseDto2.response("Fetched Car with given id " + id + " is Found", car));
    }

    //Update Car By Id
    @PatchMapping("/update")
    public ResponseEntity<CarResponseDto2> updateCar(@Valid @RequestBody CarDto carDto, @RequestParam Integer id) {
        CarDto updateCar = carService.updateCar(carDto, id);
        return ResponseEntity.ok(new CarResponseDto2<>("Car Updated Successfully", updateCar, null));
    }

    // Delete Car(Soft Delete)
    @DeleteMapping("/softDelete")
    public ResponseEntity<ResponseDto> softDelete(@RequestParam Integer id) {
        carService.softDelete(id);
        return ResponseEntity.ok(new ResponseDto("Success", "Car Deleted Successfully"));
    }

    //Delete Car(Hard Delete)
    @DeleteMapping("/hardDelete")
    public ResponseEntity<ResponseDto> hardDelete(@RequestParam Integer id) {
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
    public ResponseEntity<CarResponseDto<List<CarDto>>> getCarsByDealerIdAndCarStatus(@RequestParam Integer id,
                                                                                      @RequestParam String carStatus,
                                                                                      @RequestParam int page,
                                                                                      @RequestParam int size)
    {
        CarResponseDto<List<CarDto>> response = carService.getCarsByDealerIdAndStatus(id, carStatus, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dealer/carCount")
    public ResponseEntity<CarCountResponseDto> getNumberOfCarsByDealerIdAndCarStatus(@RequestParam Integer id,
                                                                                     @RequestParam String carStatus)
    {
        long carCount = carService.getNumberOfCarsByDealerIdAndStatus(id, carStatus);
        return ResponseEntity.ok(new CarCountResponseDto(id, carStatus,carCount));
    }

    @GetMapping("/main")
    public ResponseEntity<CarResponseDto2<CarDto>> getCarByMainCarId(@RequestParam String mainCarId)
    {
        CarDto car = carService.getCarByMainCarId(mainCarId);
        return ResponseEntity.ok(CarResponseDto2.response("Fetched Car By MainCarId " + mainCarId, car));
    }
    @GetMapping("/filterbypage")
    public ResponseEntity<CarResponseDto<List<CarDto>>> getCarsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Status status) {

        CarResponseDto<List<CarDto>> response =
                carService.getCarsWithPaginationOnlyActivePending(page, size, status);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter/all")
    public ResponseEntity<CarResponseDto<List<CarDto>>> getCarsWithoutPagination(
            @RequestParam(required = false) Status status) {

        CarResponseDto<List<CarDto>> response =
                carService.getCarsWithoutPaginationOnlyActivePending(status);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/filters")
    public CarResponseDto<List<CarDto>> filterCars(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String fuelType,
            @RequestParam(required = false) String transmission,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice
    )
    {
        return carService.filterCars(status, brand, model, city, fuelType, transmission, minPrice, maxPrice);
    }
}