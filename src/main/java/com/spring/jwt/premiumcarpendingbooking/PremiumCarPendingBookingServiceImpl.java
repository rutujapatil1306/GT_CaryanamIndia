package com.spring.jwt.premiumcarpendingbooking;

import com.spring.jwt.PremiumCarData.PremiumCar;
import com.spring.jwt.PremiumCarData.PremiumCarNotFoundException;
import com.spring.jwt.PremiumCarData.PremiumCarRepository;
import com.spring.jwt.dealer.exception.DealerNotFoundException;
import com.spring.jwt.entity.Dealer;
import com.spring.jwt.entity.PremiumCarPendingBooking;
import com.spring.jwt.entity.Status;
import com.spring.jwt.entity.User;
import com.spring.jwt.exception.ResourceNotFoundException;
import com.spring.jwt.exception.UserNotFoundExceptions;
import com.spring.jwt.premiumcar.ApiResponseDto;
import com.spring.jwt.repository.DealerRepository;
import com.spring.jwt.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PremiumCarPendingBookingServiceImpl implements PremiumCarPendingBookingService {
    private final PremiumCarPendingBookingRepository repository;
    @Autowired
    private final ConfirmBookingRepository confirmRepo;
    private final UserRepository userRepository;
    private final PremiumCarRepository premiumCarRepository;
    private final DealerRepository dealerRepository;
    @Override
    public ApiResponseDto createBooking(PremiumCarPendingBookingDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundExceptions("User not found with id " + dto.getUserId()));

        Dealer dealer = dealerRepository.findById(dto.getDealerId())
                .orElseThrow(() -> new DealerNotFoundException("Dealer not found with id " + dto.getDealerId()));

        PremiumCar premiumCar = premiumCarRepository.findById(dto.getPremiumCar())
                .orElseThrow(() -> new PremiumCarNotFoundException("PremiumCar not found with id " + dto.getPremiumCar()));

        PremiumCarPendingBooking entity = PremiumCarPendingBookingMapper.toEntity(dto, user, dealer,premiumCar);
        repository.save(entity);

        return new ApiResponseDto(
                "success",
                "Pending booking created successfully",
                200,
                "OK",
                null
        );
    }



    @Override
    public GetApiResponseDto getBookingByDealerId(Integer dealerId) {
        Dealer dealer = dealerRepository.findById(dealerId)
                .orElseThrow(() -> new DealerNotFoundException("Dealer not found with id " + dealerId));

        List<PremiumCarPendingBookingDto> bookings = repository.findByDealer(dealer)
                .stream()
                .map(PremiumCarPendingBookingMapper::toDto)
                .collect(Collectors.toList());

        return new GetApiResponseDto("success", "Bookings fetched successfully", bookings);
    }

    @Override
    public GetApiResponseDto getBookingByUserId(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundExceptions("User not found with id " + userId));

        List<PremiumCarPendingBookingDto> bookings = repository.findByUser(user)
                .stream()
                .map(PremiumCarPendingBookingMapper::toDto)
                .collect(Collectors.toList());

        return new GetApiResponseDto("success", "Bookings fetched successfully", bookings);
    }

    public GetApiResponseDto getByPendingBookingId(Long premiumCarPendingBookingId){
        try {
            PremiumCarPendingBooking bookings = repository.findById(premiumCarPendingBookingId).
                    orElseThrow(()->new RuntimeException("Booking not found"));
            return new GetApiResponseDto("success","Fetch Pending booking successfully",bookings);
        }catch (Exception e){
            return new GetApiResponseDto("failed","Error fetching booking by userId",null);
        }
    }

    @Override
    public ApiResponseDto updateBookingStatus(Long bookingId, Integer dealerId, Status status) {
        Dealer dealer = dealerRepository.findById(dealerId)
                .orElseThrow(() -> new DealerNotFoundException("Dealer not found with id " + dealerId));

        PremiumCarPendingBooking booking = repository.findById(bookingId)
                .orElseThrow(() -> new InvalidIdException("Pending booking not found with id " + bookingId));

        if (!booking.getDealer().getId().equals(dealer.getId())) {
            throw new UnauthorizedAccessException("Unauthorized: Only dealer can update this booking");
        }

        if (confirmRepo.existsByPendingBooking_PremiumCarPendingBookingId(bookingId)) {
            throw new InvalidIdException("Duplicate ID: Booking already confirmed");
        }

        booking.setStatus(String.valueOf(status));
        repository.save(booking);

        if (status == Status.APPROVED) {
            ConfirmBooking confirm = new ConfirmBooking();
            confirm.setPendingBooking(booking);
            confirm.setDealer(booking.getDealer());
            confirm.setUser(booking.getUser());
            confirm.setStatus(status);
            confirm.setConfirmedAt(LocalDate.now());
            confirmRepo.save(confirm);
        }

        return new ApiResponseDto(
                "success",
                "Update Status Successfully",
                200,
                "OK",
                null
        );
    }


}
