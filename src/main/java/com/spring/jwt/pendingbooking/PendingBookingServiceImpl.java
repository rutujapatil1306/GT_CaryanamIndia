package com.spring.jwt.pendingbooking;
import com.spring.jwt.CarConfirmBooking.CarConfirmBookingRepository;
import com.spring.jwt.dealer.exception.DealerNotFoundException;
import com.spring.jwt.entity.*;
import com.spring.jwt.Car.CarRepository;
import com.spring.jwt.exception.PageNotFoundException;
import com.spring.jwt.exception.UserNotFoundExceptions;
import com.spring.jwt.pendingbooking.DTO.PendingBookingDTO;
import com.spring.jwt.pendingbooking.Exception.PendingBookingNotFoundException;
import com.spring.jwt.pendingbooking.Exception.ResourceNotFoundExceptions;
import com.spring.jwt.premiumcar.ApiResponseDto;
import com.spring.jwt.premiumcar.exceptions.CarsNotFoundException;
import com.spring.jwt.repository.DealerRepository;
import com.spring.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    private final CarConfirmBookingRepository carConfirmBookingRepository;

    @Override
    public ApiResponseDto create(PendingBookingDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundExceptions("User not found with id :" + dto.getUserId()));

        Dealer dealer = dealerRepository.findById(dto.getDealerId())
                .orElseThrow(() -> new DealerNotFoundException("Dealer not found with id :" + dto.getDealerId()));

        Car car = carRepository.findById(dto.getCarId())
                .orElseThrow(() -> new CarsNotFoundException("car not found with id :" + dto.getCarId()));
        dto.setStatus(String.valueOf(Status.PENDING));
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

    @Override
    public List<PendingBookingDTO> getPendingBookingByDealerId(Integer dealerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dealerId").descending());

        Dealer dealer = dealerRepository.findById(dealerId).orElseThrow(() -> new DealerNotFoundException("Dealer not found at id:" + dealerId));

        long totalNoOfCars = pendingbookingrepository.countByDealerId(dealerId);
        int totalPages = (int) Math.ceil((double) totalNoOfCars / size);

        if (page >= totalPages && totalNoOfCars > 0) {
            throw new PageNotFoundException(
                    "Page " + page + " not found. Total available pages: " + totalPages
            );
        }
        List<PendingBooking> bookings = pendingbookingrepository.findByDealer_Id(dealerId, pageable);
        if(bookings.isEmpty())
        {
            throw new PendingBookingNotFoundException("Pending Booking not found for dealerId: " + dealerId);
        }
        return bookings.stream().map(booking -> pendingbookingmapper.toDTO(booking)).toList();
    }

    @Override
    public List<PendingBookingDTO> getPendingBookingByUserId(Integer userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("userId").descending());

        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundExceptions("User not Found At given Id: " + userId));

        long totalNoOfCars = pendingbookingrepository.countByUserId(userId);
        int totalPages = (int) Math.ceil((double) totalNoOfCars / size);

        if (page >= totalPages && totalNoOfCars > 0) {
            throw new PageNotFoundException(
                    "Page " + page + " not found. Total available pages: " + totalPages
            );
        }
        List<PendingBooking> bookings = pendingbookingrepository.findByUser_Id(userId, pageable);

        if(bookings.isEmpty()){
            throw new PendingBookingNotFoundException("Pending bookings not found for userId: " + userId);
        }
        return bookings.stream().map(booking -> pendingbookingmapper.toDTO(booking)).toList();
    }

    @Override
    public PendingBookingDTO getPendingBookingById(Integer id) {
        PendingBooking pendingBooking = pendingbookingrepository.findById(id).orElseThrow(() -> new PendingBookingNotFoundException("Pending booking not Found At id: " + id));
        return pendingbookingmapper.toDTO(pendingBooking);
    }

    //@Transactional
    @Override
    public PendingBookingDTO updateBookingStatusByPendingId(Integer id, Status status) {
        PendingBooking pendingBooking = pendingbookingrepository.findById(id).orElseThrow(() -> new PendingBookingNotFoundException("Pending booking not Found At id: " + id));
        pendingBooking.setStatus(status);
        PendingBooking updated = pendingbookingrepository.save(pendingBooking);

        if(status==Status.SOLD){
            CarConfirmBooking carConfirmBooking = new CarConfirmBooking();
            carConfirmBooking.setUserId(pendingBooking.getUser());
            carConfirmBooking.setDealerId(pendingBooking.getDealer());
            carConfirmBooking.setStatus(pendingBooking.getStatus());
            carConfirmBooking.setDate(LocalDate.now());
            carConfirmBooking.setCarCar(pendingBooking.getCar());
            carConfirmBooking.setPrice(pendingBooking.getPrice());
            carConfirmBooking.setAskingPrice(pendingBooking.getAskingPrice());

            //System.out.println("Saving Confirm Booking for UserId: " + pendingBooking.getUserId());
            carConfirmBookingRepository.save(carConfirmBooking);
        }

            return pendingbookingmapper.toDTO(updated);


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
        if (dto.getDealerId() != null) pendingbooking.setDealer(getDealer(dto.getDealerId()));
        if (dto.getUserId() != null) pendingbooking.setUser(getUser(dto.getUserId()));
        if (dto.getCarId() != null) pendingbooking.setCar(getCar(dto.getCarId()));
    }


}

