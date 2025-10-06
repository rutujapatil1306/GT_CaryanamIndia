package com.spring.jwt.pendingbooking;
import com.spring.jwt.dealer.exception.DealerNotFoundException;
import com.spring.jwt.entity.*;
import com.spring.jwt.Car.CarRepository;
import com.spring.jwt.exception.UserNotFoundExceptions;
import com.spring.jwt.premiumcar.ApiResponseDto;
import com.spring.jwt.premiumcar.exceptions.CarsNotFoundException;
import com.spring.jwt.repository.DealerRepository;
import com.spring.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PendingBookingServiceImpl implements PendingBookingService {
    private final PendingBookingRepository pendingbookingrepository;
    private final DealerRepository dealerRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final PendingBookingMapper pendingbookingmapper;

    @Override
    public ApiResponseDto create(PendingBookingDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundExceptions("User not found with id :" + dto.getUserId()));

        Dealer dealer = dealerRepository.findById(dto.getDealerId())
                .orElseThrow(() -> new DealerNotFoundException("Dealer not found with id :" + dto.getDealerId()));

        Car car = carRepository.findById(dto.getCarId())
                .orElseThrow(() -> new CarsNotFoundException("car not found with id :" + dto.getCarId()));
        Status status = Status.fromString(dto.getStatus());
        PendingBooking pendingbooking = pendingbookingmapper.toEntity(dto, dealer, user, car);
        pendingbookingrepository.save(pendingbooking);
        return new ApiResponseDto(
                "success",
                "Pending booking created successfully for userId: " + dto.getUserId(),
                201,
                "CREATED",
                null
        );
    }

    @Override
    public PendingBookingDTO getById(Integer id) {
        PendingBooking pendingbooking = pendingbookingrepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("PendingBooking not found with ID: " + id));
        return pendingbookingmapper.toDTO(pendingbooking);
    }

    @Override
    public List<PendingBookingDTO> getAll() {
        return pendingbookingrepository.findAll()
                .stream()
                .map(pendingbookingmapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ApiResponseDto update(Integer id, PendingBookingDTO dto) {
        PendingBooking pendingbooking = pendingbookingrepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "PendingBooking not found with ID: " + id));

        pendingbooking.setDate(dto.getDate());
        pendingbooking.setPrice(dto.getPrice());
        pendingbooking.setAskingPrice(dto.getAskingPrice());
        pendingbooking.setStatus(Status.fromString(dto.getStatus()));
        setEntities(pendingbooking,dto);
        pendingbookingrepository.save(pendingbooking);
        return new ApiResponseDto(
                "success",
                "Pending booking updated successfully for ID: " + id,
                200,
                "OK",
                null
        );
    }

    @Override
    public void delete(Integer id) {
        if (!pendingbookingrepository.existsById(id)) {
            throw new ResourceNotFoundExceptions("PendingBooking not found with ID: " + id);
        }
        pendingbookingrepository.deleteById(id);
    }

    private Dealer getDealer(Integer dealerId) {
        return dealerRepository.findById(dealerId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Dealer not found with ID: " + dealerId));
    }

    private User getUser(Integer userId) {
        return userRepository.findById(Integer.valueOf(userId))
                .orElseThrow(() -> new ResourceNotFoundExceptions("User not found with ID: " + userId));
    }

    private Car getCar(Integer carId) {
        return carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Car not found with ID: " + carId));
    }
    private void setEntities(PendingBooking pendingbooking,PendingBookingDTO dto){
        if (dto.getDealerId() != null) pendingbooking.setDealerId(getDealer(dto.getDealerId()));
        if (dto.getUserId() != null) pendingbooking.setUserId(getUser(dto.getUserId()));
        if (dto.getCarId() != null) pendingbooking.setCarCar(getCar(dto.getCarId()));
    }
}

