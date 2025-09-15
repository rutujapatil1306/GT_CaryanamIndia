package com.spring.jwt.Car;
import com.spring.jwt.Car.DTO.CarDto;
import com.spring.jwt.Car.DTO.CarResponseDto;
import com.spring.jwt.Car.Exception.CarAlreadyExistsException;
import com.spring.jwt.Car.Exception.CarNotFoundException;
import com.spring.jwt.Car.Exception.StatusNotFoundException;
import com.spring.jwt.entity.Car;
import com.spring.jwt.exception.PageNotFoundException;
import com.spring.jwt.repository.DealerRepository;
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
    DealerRepository dealerRepository;

    @Autowired
    CarMapper carMapper;

    @Override
    public CarDto addCar(CarDto cardto) {

        Car car = carMapper.toEntity(cardto);
        Car savedCar = carRepository.save(car);
        String mainCarId = generateMainCarId(savedCar);
        savedCar.setMainCarId(mainCarId);
        boolean exists = carRepository.existsByMainCarId(mainCarId);
        if(exists)
        {
            throw  new CarAlreadyExistsException("Car Already Exists for id: " + mainCarId);
        }

        Car addedCar = carRepository.save(savedCar);
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
        car.setCarStatus(Status.INACTIVE);
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
        if(cars.isEmpty())
        {
            throw  new CarNotFoundException("Car Not found for given Status " + carStatus + " on Page Number " + page);
        }
        List<CarDto> dtos = cars.stream().map(c -> carMapper.toDto(c)).toList();
        //long totalNoOfCars = carRepository.countByCarStatus(status);
        return new CarResponseDto<>("List of cars with status " + carStatus + " on page number " + page, dtos, null, totalNoOfCars);

    }

    @Override
    public CarResponseDto<List<CarDto>> getCarsByDealerIdAndStatus(Integer dealerId, String carStatus, int page, int size) {

        Status status;

        // Convert string to enum safely
        try {
            status = Status.valueOf(carStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new StatusNotFoundException("Invalid car status: " + carStatus);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("dealerId").descending());

//        boolean dealerExists = dealerRepository.existsById(dealerId);
//        if (!dealerExists) {
//            throw new DealerNotFoundExceptions("Dealer not found with ID: " + dealerId);
//        }

        long totalNoOfCars = carRepository.countByDealerIdAndCarStatus(dealerId, status);
        int totalPages = (int) Math.ceil((double) totalNoOfCars / size);

        if (page >= totalPages && totalNoOfCars > 0) {
            throw new PageNotFoundException(
                    "Page " + page + " not found. Total available pages: " + totalPages
            );
        }

        List<Car> allCars = carRepository.findByDealerIdAndCarStatus(dealerId, status, pageable);

        if (allCars.isEmpty())
        {
            throw new CarNotFoundException("No cars found for dealerId: " + dealerId + " and status: " + carStatus);
        }
        List<CarDto> cars = allCars.stream().map(c -> carMapper.toDto(c)).toList();
        //long totalNoOfCars = carRepository.countByDealerIdAndCarStatus(dealerId, status);

        return new CarResponseDto<>("Cars for DealerId " + dealerId + " with status " + status + " on page number " + page, cars, null, totalNoOfCars);
    }

    @Override
    public CarResponseDto<List<CarDto>> getCarsWithPaginationOnlyActivePending(int page, int size) {
        List<Status> allowedStatuses = List.of(Status.PENDING, Status.ACTIVE);
        Pageable pageable = PageRequest.of(page, size);
        List<Car> cars = carRepository.findByCarStatusIn(allowedStatuses)
                .stream()
                .skip(page * size)
                .limit(size)
                .toList();
        if (cars.isEmpty()) {
            throw new CarNotFoundException("No cars found with status PENDING or ACTIVE on page " + page);
        }
        List<CarDto> dtos = cars.stream().map(carMapper::toDto).toList();
        long totalCars = carRepository.countByCarStatus(Status.PENDING) + carRepository.countByCarStatus(Status.ACTIVE);
        return new CarResponseDto<>("Cars with status PENDING or ACTIVE", dtos, null, totalCars);
    }

    @Override
    public CarResponseDto<List<CarDto>> getCarsWithoutPaginationOnlyActivePending() {
        List<Status> allowedStatuses = List.of(Status.PENDING, Status.ACTIVE);
        List<Car> cars = carRepository.findByCarStatusIn(allowedStatuses);
        if (cars.isEmpty()) {
            throw new CarNotFoundException("No cars found with status PENDING or ACTIVE");
        }
        List<CarDto> dtos = cars.stream().map(carMapper::toDto).toList();
        long totalCars = cars.size();
        return new CarResponseDto<>("Cars with status PENDING or ACTIVE", dtos, null, totalCars);
    }


    public long getNumberOfCarsByDealerIdAndStatus(Integer dealerId, String carStatus) {

        Status status;

        try {
            status = Status.valueOf(carStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CarNotFoundException("Invalid car status: " + carStatus);
        }

        return carRepository.countByDealerIdAndCarStatus(dealerId, status);


    }

    public String generateMainCarId(Car car) {
        String brandInitials = car.getBrand().replaceAll("\\s+", "").substring(0, 2).toUpperCase();
        String modelInitials = car.getModel().replaceAll("\\s+", "").substring(0, 2).toUpperCase();
        String yearSuffix = String.valueOf(car.getYear()).substring(2);

        return brandInitials + modelInitials + yearSuffix + "-" + car.getId();
    }

    @Override
    public CarDto getCarByMainCarId(String mainCarId) {
        Car car = carRepository.findByMainCarId(mainCarId).orElseThrow(() -> new CarNotFoundException("Car Not Found With MainCarId " + mainCarId));
        return carMapper.toDto(car);
    }
}
