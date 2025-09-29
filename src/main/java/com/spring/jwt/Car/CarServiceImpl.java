package com.spring.jwt.Car;

import com.spring.jwt.Car.DTO.CarDto;
import com.spring.jwt.Car.DTO.CarFilterDTO;
import com.spring.jwt.Car.DTO.CarResponseDto;
import com.spring.jwt.Car.Exception.CarAlreadyExistsException;
import com.spring.jwt.Car.Exception.CarNotFoundException;
import com.spring.jwt.Car.Exception.InvalidStatusException;
import com.spring.jwt.Car.Exception.StatusNotFoundException;
//import com.spring.jwt.dealer.DealerNotFoundException;
import com.spring.jwt.dealer.exception.DealerNotFoundException;
import com.spring.jwt.entity.Car;
import com.spring.jwt.entity.Dealer;
import com.spring.jwt.exception.PageNotFoundException;
import com.spring.jwt.repository.DealerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    CarRepository carRepository;

    @Autowired
    DealerRepository dealerRepository;


    @Autowired
    CarMapper carMapper;

    @Override
    public CarDto addCar(CarDto cardto) {

        if (cardto.getDealerId() == null) {
            throw new IllegalArgumentException("DealerId is required for adding a car");
        }
        Dealer dealer = dealerRepository.findById(cardto.getDealerId())
                .orElseThrow(() -> new DealerNotFoundException("Dealer not found with id: " + cardto.getDealerId()));

        if (cardto.getCarStatus() == null) {
            throw new IllegalArgumentException("Car status is required");
        }
        Status status;
        try {
            status = Status.valueOf(cardto.getCarStatus().toString().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusException("Invalid car status: " + cardto.getCarStatus());
        }
        Car car = carMapper.toEntity(cardto);
        car.setDealer(dealer);


        Car savedCar = carRepository.save(car);
        String mainCarId = generateMainCarId(savedCar);
        savedCar.setMainCarId(mainCarId);
        boolean exists = carRepository.existsByMainCarId(mainCarId);
        if (exists) {
            throw new CarAlreadyExistsException("Car Already Exists for id: " + mainCarId);
        }

        if (cardto.getCarStatus() == null) {
            throw new StatusNotFoundException("Car status cannot be null");
        }

        Car addedCar = carRepository.save(savedCar);
        CarDto responseDto = carMapper.toDto(addedCar);

        responseDto.setDealerId(addedCar.getDealer().getId());
        return responseDto;
    }

    @Override
    public CarDto getCarById(int id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException("Car Not Found At id " + id));

        return carMapper.toDto(car);
    }

    @Override
    public CarDto updateCar(CarDto carDto, int id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException("Car Not Found At Id: " + id));

        if (carDto.getPrice() != null) {
            if (carDto.getPrice() <= 0) {
                throw new IllegalArgumentException("Price must be greater than 0");
            }
            if (!carDto.getPrice().equals(car.getPrice())) {
                car.setPrice(carDto.getPrice());
            }
        }
        if (carDto.getCarInsuranceDate() != null) {
            if (carDto.getCarInsuranceDate().equals(car.getCarInsuranceDate())) {
                throw new IllegalArgumentException(
                        "Car insurance date " + carDto.getCarInsuranceDate() + " is already set for this car");
            }
            car.setCarInsuranceDate(carDto.getCarInsuranceDate());
        }


        carMapper.updateCarFromDto(car, carDto);


        if (carDto.getDealerId() != null) {
            Dealer dealer = dealerRepository.findById(carDto.getDealerId())
                    .orElseThrow(() -> new DealerNotFoundException("Dealer Not Found At Id: " + carDto.getDealerId()));
            car.setDealer(dealer);
        }

        Car updatedCar = carRepository.save(car);
        return carMapper.toDto(updatedCar);

    }

    @Override
    public void softDelete(int id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException("Car Not Found At id " + id));

        //set Car Status to DELETED for Soft Delete
        car.setCarStatus(Status.INACTIVE);
        carRepository.save(car);
    }

    @Override
    public void hardDelete(int id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException("Car Not Found At id " + id));
        carRepository.delete(car);
    }

    @Override
    public CarResponseDto<List<CarDto>> getAllCarsByStatus(String carStatus, int page, int size) {
        Status status;
        // Convert String to Enum safely
        try {
            status = Status.valueOf(carStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new StatusNotFoundException("Invalid car status: " + carStatus);
        }

        Pageable pageable = PageRequest.of(page, size);

        long totalNoOfCars = carRepository.countByCarStatus(status);
        int totalPages = (int) Math.ceil((double) totalNoOfCars / size);

        if (page >= totalPages && totalNoOfCars > 0) {
            throw new PageNotFoundException(
                    "Page " + page + " not found. Total available pages: " + totalPages
            );
        }

        List<Car> cars = carRepository.findByCarStatus(status, pageable);
        if (cars.isEmpty()) {
            throw new CarNotFoundException("Car Not found for given Status " + carStatus + " on Page Number " + page);
        }
        List<CarDto> dtos = cars.stream().map(c -> carMapper.toDto(c)).toList();
        //long totalNoOfCars = carRepository.countByCarStatus(status);
        return new CarResponseDto<>("List of cars with status " + carStatus + " on page number " + page, dtos, null, totalNoOfCars);

    }

    @Override
    public CarResponseDto<List<CarDto>> getCarsByDealerIdAndStatus(Integer id, String carStatus, int page, int size) {

        Status status;

        // Convert string to enum safely
        try {
            status = Status.valueOf(carStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new StatusNotFoundException("Invalid Car Status: " + carStatus);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("dealerId").descending());

        Dealer dealer = dealerRepository.findById(id)
                .orElseThrow(() -> new DealerNotFoundException("Dealer with ID " + id + " not found"));

        long totalNoOfCars = carRepository.countByDealerIdAndCarStatus(id, status);
        int totalPages = (int) Math.ceil((double) totalNoOfCars / size);

        if (page >= totalPages && totalNoOfCars > 0) {
            throw new PageNotFoundException(
                    "Page " + page + " not found. Total available pages: " + totalPages
            );
        }

        List<Car> allCars = carRepository.findByDealerIdAndCarStatus(id, status, pageable);

        if (allCars.isEmpty()) {
            throw new CarNotFoundException("No cars found for dealerId: " + id + " and status: " + carStatus);
        }
        List<CarDto> cars = allCars.stream().map(c -> carMapper.toDto(c)).toList();
        //long totalNoOfCars = carRepository.countByDealerIdAndCarStatus(dealerId, status);

        return new CarResponseDto<>("Cars for DealerId " + id + " with status " + status + " on page number " + page, cars, null, totalNoOfCars);
    }

    @Override
    public long getNumberOfCarsByDealerIdAndStatus(Integer id, String carStatus) {

        Dealer dealer = dealerRepository.findById(id)
                .orElseThrow(() -> new DealerNotFoundException("Dealer with ID " + id + " not found"));
        Status status;

        try {
            status = Status.valueOf(carStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new StatusNotFoundException("Invalid car status: " + carStatus);
        }

        return carRepository.countByDealerIdAndCarStatus(id, status);


    }

    public String generateMainCarId(Car car) {
        String brandInitials = car.getBrand().replaceAll("\\s+", "").substring(0, 2).toUpperCase();
        String modelInitials = car.getModel().replaceAll("\\s+", "").substring(0, 2).toUpperCase();
        String yearSuffix = String.valueOf(car.getYear()).substring(2);

        return brandInitials + modelInitials + yearSuffix + "-" + car.getId();
    }

    //*    @Override
    public CarDto getCarByMainCarId(String mainCarId) {
        Car car = carRepository.findByMainCarId(mainCarId).orElseThrow(() -> new CarNotFoundException("Car Not Found With MainCarId " + mainCarId));
        return carMapper.toDto(car);
    }

    @Override
    public CarResponseDto<List<CarDto>> getCarsWithPaginationOnlyActivePending(int page, int size, Status status) {
        // Validate pagination
        if (page < 0 || size <= 0) {
            throw new PageNotFoundException("Page must be >= 0 and size must be > 0");
        }

        //Allowed statuses
        List<Status> allowedStatuses = List.of(Status.PENDING, Status.ACTIVE);

        // Validate input status
        if (status != null && !allowedStatuses.contains(status)) {
            throw new InvalidStatusException(
                    "Status must be either PENDING or ACTIVE. Provided: " + status);
        }

        // Build status list to fetch
        List<Status> statusesToFetch = (status != null) ? List.of(status) : allowedStatuses;

        // Fetch cars with pagination
        Pageable pageable = PageRequest.of(page, size);
        var carPage = carRepository.findByCarStatusIn(statusesToFetch, pageable);
        List<Car> cars = carPage.getContent();

        // If no cars found
        if (cars.isEmpty()) {
            throw new CarNotFoundException("No cars found for status: "
                    + (status != null ? status : "PENDING or ACTIVE") + " on page " + page);
        }

        // Map to DTOs
        List<CarDto> dtos = cars.stream().map(carMapper::toDto).toList();

        // Count total cars for pagination info
        long totalCars = carRepository.countByCarStatusIn(statusesToFetch);

        return new CarResponseDto<>(
                "Cars with status " + (status != null ? status : "PENDING/ACTIVE"),
                dtos,
                null,
                totalCars
        );
    }

    @Override
    public CarResponseDto<List<CarDto>> getCarsWithoutPaginationOnlyActivePending(Status status) {
        // Allowed statuses
        List<Status> allowedStatuses = List.of(Status.PENDING, Status.ACTIVE);

        // Validate input status
        if (status != null && !allowedStatuses.contains(status)) {
            throw new InvalidStatusException(
                    "Invalid status: " + status + ". Allowed: PENDING, ACTIVE");
        }

        //Build status list to fetch
        List<Status> statusesToFetch = (status != null) ? List.of(status) : allowedStatuses;

        // Fetch cars without pagination
        List<Car> cars = carRepository.findByCarStatusIn(statusesToFetch);

        if (cars.isEmpty()) {
            throw new CarNotFoundException("No cars found with status "
                    + (status != null ? status : "PENDING or ACTIVE"));
        }

        // Map to DTOs
        List<CarDto> dtos = cars.stream().map(carMapper::toDto).toList();

        long totalCars = cars.size();

        return new CarResponseDto<>(
                "Cars with status " + (status != null ? status : "PENDING or ACTIVE"),
                dtos,
                null,
                totalCars
        );
    }

    @Override
    public CarResponseDto<List<CarDto>> filterCars(Status status, String brand, String model, String city,
                                                   String fuelType, String transmission,
                                                   Integer minPrice, Integer maxPrice) {

        // Build filter object
        CarFilterDTO filter = new CarFilterDTO();
        filter.setStatus(status);
        filter.setBrand(brand);
        filter.setModel(model);
        filter.setCity(city);
        filter.setFuelType(fuelType);
        filter.setTransmission(transmission);
        filter.setMinPrice(minPrice);
        filter.setMaxPrice(maxPrice);

        // Fetch cars using Specification
        List<Car> cars = carRepository.findAll(CarSpecifications.withFilters(filter));

        if (cars.isEmpty()) {
            throw new CarNotFoundException("No cars found with the given filters");
        }

        // Map to DTOs
        List<CarDto> dtos = new ArrayList<>();
        for (Car car : cars) {
            dtos.add(carMapper.toDto(car));
        }

        return new CarResponseDto<>(
                "Cars fetched successfully with filters",
                dtos,
                null,
                dtos.size()
        );
    }
}
