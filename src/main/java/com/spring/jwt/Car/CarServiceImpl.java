package com.spring.jwt.Car;

import com.spring.jwt.Car.DTO.CarDto;
import com.spring.jwt.Car.DTO.CarResponseDto;
import com.spring.jwt.Car.Exception.CarAlreadyExistsException;
import com.spring.jwt.Car.Exception.CarNotFoundException;
import com.spring.jwt.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService{

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarMapper carMapper;

    @Override
    public CarDto addCar(CarDto cardto) {
        boolean exists = carRepository.existsByMainCarId(cardto.getMainCarId());
        if(exists)
        {
            throw  new CarAlreadyExistsException("Car Already Exists");
        }
        Car addedCar = carRepository.save(carMapper.toEntity(cardto));;
        return carMapper.toDto(addedCar);
    }

    @Override
    public CarDto getCarById(int id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException("Car Not Found At id " + id));

        return carMapper.toDto(car);
    }

    @Override
    public CarDto updateCar(CarDto carDto, int id) {
        Car car = carRepository.findById(id).orElseThrow(()-> new CarNotFoundException("Car Not Found At Id: " + id));

        //Update Fields

        //Validated Fields Are checked to be null or not if not null then only update fields
         if(carDto.getAbs() != null){car.setAbs(carDto.getAbs());}
         if(carDto.getBrand() != null){car.setBrand(carDto.getBrand());}
         if(carDto.getCarStatus() != null){car.setCarStatus(carDto.getCarStatus());}
         if(carDto.getPrice() != 0){car.setPrice(carDto.getPrice());}
         if(carDto.getDealerId() != 0){car.setDealerId(carDto.getDealerId());}
         if(carDto.getMainCarId() != null){car.setMainCarId(carDto.getMainCarId());}
         if(carDto.getCarType() != null){car.setCarType(carDto.getCarType());}

         //other fields updated as it is
         car.setAirbag(carDto.getAirbag());
         car.setButtonStart(carDto.getButtonStart());
         car.setSunroof(carDto.getSunroof());
         car.setChildSafetyLocks(carDto.getChildSafetyLocks());
         car.setAcFeature(carDto.getAcFeature());
         car.setMusicFeature(carDto.getMusicFeature());
         car.setArea(carDto.getArea());
         car.setVariant(carDto.getVariant());
         car.setCarInsurance(carDto.getCarInsurance());
         car.setCarInsuranceDate(carDto.getCarInsuranceDate());
         car.setCarInsuranceType(carDto.getCarInsuranceType());
         car.setPendingApproval(carDto.isPendingApproval());
         car.setCity(carDto.getCity());
         car.setColor(carDto.getColor());
         car.setDescription(carDto.getDescription());
         car.setFuelType(carDto.getFuelType());
         car.setKmDriven(carDto.getKmDriven());
         car.setModel(carDto.getModel());
         car.setOwnerSerial(carDto.getOwnerSerial());
         car.setPowerWindowFeature(carDto.getPowerWindowFeature());
         car.setRearParkingCameraFeature(carDto.getRearParkingCameraFeature());
         car.setRegistration(carDto.getRegistration());
         car.setTitle(carDto.getTitle());
         car.setTransmission(carDto.getTransmission());
         car.setYear(carDto.getYear());
         car.setDate(carDto.getDate());
         car.setCarPhotoId(carDto.getCarPhotoId());


         Car updatedCar = carRepository.save(car);
         return carMapper.toDto(updatedCar);


    }

    @Override
    public void softDelete(int id) {
        Car car = carRepository.findById(id).orElseThrow(()-> new CarNotFoundException("Car Not Found At id " + id));

        //set Car Status to DELETED for Soft Delete
        car.setCarStatus(Status.DELETED);
        carRepository.save(car);
    }

    @Override
    public void hardDelete(int id) {
        Car car = carRepository.findById(id).orElseThrow(()-> new CarNotFoundException("Car Not Found At id " + id));
        carRepository.delete(car);
    }

    @Override
    public CarResponseDto<List<CarDto>> getAllCarsByStatus(String carStatus , int page, int size) {
        Status status;
        // Convert String to Enum safely
        try {
            status = Status.valueOf(carStatus.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            throw new CarNotFoundException("Invalid car status: " + carStatus);
        }

        Pageable pageable = PageRequest.of(page, size);

        List<Car> cars = carRepository.findByCarStatus(status, pageable);
        if(cars.isEmpty())
        {
            throw  new CarNotFoundException("Car Not found for given Status " + carStatus + " on Page Number " + page);
        }
        List<CarDto> dtos = cars.stream().map(c -> carMapper.toDto(c)).toList();
        long totalNoOfCars = carRepository.countByCarStatus(status);
        return new CarResponseDto<>("List of cars with status " + carStatus + " on page number " + page, dtos, null, totalNoOfCars);

    }

    @Override
    public CarResponseDto<List<CarDto>> getCarsByDealerIdAndStatus(Integer dealerId, String carStatus, int page, int size) {

        Status status;

        // Convert string to enum safely
        try {
            status = Status.valueOf(carStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CarNotFoundException("Invalid car status: " + carStatus);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("dealerId").descending());

        List<Car> allCars = carRepository.findByDealerIdAndCarStatus(dealerId, status, pageable);

        if (allCars.isEmpty())
        {
            throw new CarNotFoundException("No cars found for dealerId: " + dealerId + " and status: " + carStatus);
        }
        List<CarDto> cars = allCars.stream().map(c -> carMapper.toDto(c)).toList();
        long totalNoOfCars = carRepository.countByDealerIdAndCarStatus(dealerId, status);

        return new CarResponseDto<>("Cars for DealerId " + dealerId + " with status " + status + " on page number " + page, cars, null, totalNoOfCars);
    }
}
