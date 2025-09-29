package com.spring.jwt.pendingbooking;
import com.spring.jwt.entity.Car;
import com.spring.jwt.entity.Dealer;
import com.spring.jwt.entity.PendingBooking;
import com.spring.jwt.entity.User;
import com.spring.jwt.Car.CarRepository;
import com.spring.jwt.premiumcar.ApiResponseDto;
import com.spring.jwt.repository.DealerRepository;
import com.spring.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PendingBookingServiceImpl implements PendingBookingService {

    private final PendingBookingRepository repository;
    private final DealerRepository dealerRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final PendingBookingMapper mapper;

    @Override
    public ApiResponseDto create(PendingBookingDTO dto) {
        Dealer dealer = getDealer(dto.getDealerId());
        User user = getUser(dto.getUserId());
        Car car = getCar(dto.getCarId());

        if (!(dto.getStatus().name().equalsIgnoreCase("PENDING") ||
                dto.getStatus().name().equalsIgnoreCase("APPROVED") ||
                dto.getStatus().name().equalsIgnoreCase("REJECTED"))) {
            throw new IllegalArgumentException("Invalid status: " + dto.getStatus() +
                    ". Allowed values: PENDING, APPROVED, REJECTED");
        }

        PendingBooking pb = mapper.toEntity(dto, dealer, user, car);
        repository.save(pb);

        // Return success response
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
        PendingBooking pb = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("PendingBooking not found with ID: " + id));
        return mapper.toDTO(pb);
    }

    @Override
    public List<PendingBookingDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ApiResponseDto update(Integer id, PendingBookingDTO dto) {
        PendingBooking pb = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("PendingBooking not found with ID: " + id));

        pb.setDate(dto.getDate());
        pb.setPrice(dto.getPrice());
        pb.setAskingPrice(dto.getAskingPrice());
        pb.setStatus(dto.getStatus());

        if (dto.getDealerId() != null) pb.setDealerId(getDealer(dto.getDealerId()));
        if (dto.getUserId() != null) pb.setUserId(getUser(dto.getUserId()));
        if (dto.getCarId() != null) pb.setCarCar(getCar(dto.getCarId()));

        repository.save(pb);

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
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundExceptions("PendingBooking not found with ID: " + id);
        }
        repository.deleteById(id);
    }

    private Dealer getDealer(Integer dealerId) {
        if (dealerId == null) return null;
        return dealerRepository.findById(dealerId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Dealer not found with ID: " + dealerId));
    }

    private User getUser(Integer userId) {
        if (userId == null) return null;
        return userRepository.findById(Integer.valueOf(userId))
                .orElseThrow(() -> new ResourceNotFoundExceptions("User not found with ID: " + userId));
    }

    private Car getCar(Integer carId) {
        if (carId == null) return null;
        return carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Car not found with ID: " + carId));
    }
}

