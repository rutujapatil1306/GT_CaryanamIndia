package com.spring.jwt.CarConfimBookingTest;

import com.spring.jwt.Car.CarRepository;
import com.spring.jwt.Car.CarStatus;
import com.spring.jwt.CarConfirmBooking.*;
import com.spring.jwt.entity.*;
import com.spring.jwt.exception.UserNotFoundExceptions;
import com.spring.jwt.pendingbooking.PendingBookingRepository;
import com.spring.jwt.repository.DealerRepository;
import com.spring.jwt.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;

@ExtendWith(MockitoExtension.class)
public class CarConfirmBookingServiceImplTest {

    @Mock
    private CarConfirmBookingRepository carConfirmBookingRepository;

    @Mock
    private CarConfirmBookingMapper carConfirmBookingMapper;

    @Mock
    private CarRepository carRepository;

    @Mock
    private PendingBookingRepository pendingBookingRepository;

    @Mock
    TempPendingBookingReqRepository tempPendingBookingReqRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    DealerRepository dealerRepository;

    @InjectMocks
    private CarConfirmBookingServiceImpl carConfirmBookingService;

    @Test
    void testCreateCarConfirmBooking_Success() {
        Integer pendingBookingId = 1;
        Car car = new Car();
        car.setId(1);
        car.setCarStatus(CarStatus.PENDING);
        Dealer dealer = new Dealer();
        dealer.setId(20);
        User user = new User();
        user.setId(30);


        PendingBooking pendingBookingToConfirm = new PendingBooking();
        pendingBookingToConfirm.setId(pendingBookingId);
        pendingBookingToConfirm.setCar(car);
        pendingBookingToConfirm.setDealer(dealer);
        pendingBookingToConfirm.setUser(user);
        pendingBookingToConfirm.setStatus(Status.SOLD);
        pendingBookingToConfirm.setDate(LocalDate.now());
        pendingBookingToConfirm.setPrice(100);
        pendingBookingToConfirm.setAskingPrice(89);


        PendingBooking otherBooking = new PendingBooking();
        otherBooking.setId(2);
        otherBooking.setCar(car);
        otherBooking.setDealer(dealer);
        otherBooking.setUser(user);
        otherBooking.setStatus(Status.PENDING);
        otherBooking.setDate(LocalDate.now());
        otherBooking.setPrice(100);
        otherBooking.setAskingPrice(88);

        List<PendingBooking> relatedBookings = List.of(pendingBookingToConfirm, otherBooking);

        CarConfirmBooking savedBooking = new CarConfirmBooking();
        savedBooking.setId(12);
        savedBooking.setCarCar(car);
        savedBooking.setDealerId(dealer);
        savedBooking.setUserId(user);

        CarConfirmBookingDto savedBookingDto = new CarConfirmBookingDto();
        savedBookingDto.setCarCar(car);



        Mockito.when(pendingBookingRepository.findById(pendingBookingId)).thenReturn(Optional.of(pendingBookingToConfirm));
        Mockito.when(pendingBookingRepository.findByCarId(car.getId())).thenReturn(relatedBookings);
        Mockito.when(carConfirmBookingRepository.save(Mockito.<CarConfirmBooking>any())).thenReturn(savedBooking);
        Mockito.when(carConfirmBookingMapper.toDto((CarConfirmBooking) any(CarConfirmBooking.class))).thenReturn(savedBookingDto);
        Mockito.when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));

        CarConfirmBookingDto result = carConfirmBookingService.confirmBooking(pendingBookingId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(savedBookingDto.getId(),result.getId());
        Assertions.assertEquals(CarStatus.SOLD, car.getCarStatus());

        Mockito.verify(pendingBookingRepository).findById(pendingBookingId);
        Mockito.verify(pendingBookingRepository).findByCarId(car.getId());
        Mockito.verify(carConfirmBookingRepository).save(Mockito.<CarConfirmBooking>any());
        Mockito.verify(carConfirmBookingMapper).toDto(any(CarConfirmBooking.class));
        Mockito.verify(tempPendingBookingReqRepository).saveAll(anyList());
        Mockito.verify(pendingBookingRepository).deleteAll(anyList());
        Mockito.verify(carRepository).saveAndFlush(car);

    }

    @Test
    void testDeleteConfirmBooking_within30days(){
        Integer carConfirmBookingId = 1, userId = 1, dealerId =2;
        Integer carId = 10;

        Car car = new Car();
        car.setId(carId);
        car.setCarStatus(CarStatus.SOLD);

        User user = new User();
        user.setId(userId);

        Dealer dealer = new Dealer();
        dealer.setId(dealerId);

        CarConfirmBooking carConfirmBooking = new CarConfirmBooking();
        carConfirmBooking.setId(carConfirmBookingId);
        carConfirmBooking.setCarCar(car);
        carConfirmBooking.setPendingBookingId(carConfirmBooking.getPendingBookingId());
        carConfirmBooking.setStatus(Status.APPROVED);
        carConfirmBooking.setPrice(100);
        carConfirmBooking.setAskingPrice(85);
        carConfirmBooking.setDate(LocalDate.now());
        carConfirmBooking.setUserId(user);
        carConfirmBooking.setDealerId(dealer);

        TempPendingBookingReq temp = new TempPendingBookingReq();
        temp.setCarId(car.getId());
        temp.setTempPendingBookingReqId(1);
        temp.setUserId(user.getId());
        temp.setDealerId(dealer.getId());
        temp.setStatus(Status.PENDING);
        temp.setDate(LocalDate.now());
        temp.setCreatedAt(LocalDateTime.now());
        temp.setPrice(100);
        temp.setAskingPrice(83);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(dealerRepository.findById(dealerId)).thenReturn(Optional.of(dealer));
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        Mockito.when(carConfirmBookingRepository.findById(carConfirmBookingId)).thenReturn(Optional.of(carConfirmBooking));
        Mockito.when(tempPendingBookingReqRepository.findByCarConfirmBookingId(carConfirmBookingId)).thenReturn(List.of(temp));

        carConfirmBookingService.deleteConfirmBooking(carConfirmBookingId);

        Mockito.verify(userRepository).findById(userId);
        Mockito.verify(dealerRepository).findById(dealerId);
        Mockito.verify(carRepository).findById(carId);
        Mockito.verify(tempPendingBookingReqRepository, Mockito.times(1)).findByCarConfirmBookingId(carConfirmBookingId);
        Mockito.verify(pendingBookingRepository, Mockito.times(1)).save(any(PendingBooking.class));
        Mockito.verify(carRepository, Mockito.times(1)).saveAndFlush(any(Car.class));
        Mockito.verify(carConfirmBookingRepository, Mockito.times(1)).delete(carConfirmBooking);


    }
}
