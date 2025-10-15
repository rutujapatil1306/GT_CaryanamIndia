package com.spring.jwt.CarConfirmBooking;

import com.spring.jwt.Car.CarRepository;
import com.spring.jwt.Car.Exception.CarNotFoundException;
import com.spring.jwt.dealer.exception.DealerNotFoundException;
import com.spring.jwt.entity.*;
import com.spring.jwt.exception.UserNotFoundExceptions;
import com.spring.jwt.pendingbooking.Exception.PendingBookingNotFoundException;
import com.spring.jwt.pendingbooking.PendingBookingRepository;
import com.spring.jwt.repository.DealerRepository;
import com.spring.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.spring.jwt.Car.CarStatus;


@Service
public class CarConfirmBookingServiceImpl implements CarConfirmBookingService{

    @Autowired
    PendingBookingRepository pendingBookingRepository;

    @Autowired
    CarConfirmBookingRepository carConfirmBookingRepository;

    @Autowired
    TempPendingBookingReqRepository tempPendingBookingReqRepository;

    @Autowired
    CarConfirmBookingMapper carConfirmBookingMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DealerRepository dealerRepository;

    @Autowired
    CarRepository carRepository;


    @Override
    public CarConfirmBookingDto confirmBooking(Integer pendingBookingId){ // UserServiceImpl userDetails) {
//        UserProfileDTO currentUser = userDetails.getCurrentUserProfile();
//        UserDTO user = currentUser.getUser();
//        Integer userId = Integer.valueOf(user.getUserId());
//        Dealer dealer = dealerRepository.findByUserId(userId);
//        if(dealer == null)
//        {
//            throw new DealerNotFoundException("Dealer not found for user id: " + userId);
//        }
        PendingBooking pendingBooking = pendingBookingRepository.findById(pendingBookingId).orElseThrow(()-> new PendingBookingNotFoundException("Pending Booking not Found At pendingId: " + pendingBookingId));

        Car car = pendingBooking.getCar();

        CarConfirmBooking carConfirmBooking = new CarConfirmBooking();
        carConfirmBooking.setUserId(pendingBooking.getUser());
        carConfirmBooking.setDealerId(pendingBooking.getDealer());
        carConfirmBooking.setCarCar(pendingBooking.getCar());
        carConfirmBooking.setStatus(Status.SOLD);
        carConfirmBooking.setDate(LocalDate.now());
        carConfirmBooking.setPrice(pendingBooking.getPrice());
        carConfirmBooking.setAskingPrice(pendingBooking.getAskingPrice());
        carConfirmBooking.setPendingBookingId(pendingBooking.getId());
        carConfirmBookingRepository.save(carConfirmBooking);
        pendingBooking.setStatus(Status.SOLD);

        List<PendingBooking> relatedBookings = pendingBookingRepository.findByCarId(car.getId());

        List<TempPendingBookingReq> backups = new ArrayList<>();
        for(PendingBooking b : relatedBookings){
            if(!b.getId().equals(pendingBookingId)) {
                TempPendingBookingReq backup = new TempPendingBookingReq();
                backup.setUserId(b.getUser().getId());
                backup.setDealerId(b.getDealer().getId());
                backup.setCarId(b.getCar().getId());
                backup.setDate(LocalDate.now());
                backup.setPrice(b.getPrice());
                backup.setAskingPrice(b.getAskingPrice());
                backup.setStatus(b.getStatus());
                backup.setCreatedAt(LocalDateTime.now());
                backup.setCarConfirmBookingId(carConfirmBooking.getId());

                backups.add(backup);
            }

        }
        tempPendingBookingReqRepository.saveAll(backups);

        pendingBookingRepository.deleteAll(relatedBookings);

        updateCarStatus(car.getId(), CarStatus.SOLD);

        return carConfirmBookingMapper.toDto(carConfirmBooking);
    }

    @Transactional
    @Override
    public void deleteConfirmBooking(Integer carConfirmBookingId) {
        System.out.println("Transaction active: " + TransactionSynchronizationManager.isActualTransactionActive());
        System.out.println("SecurityContext user: " + SecurityContextHolder.getContext().getAuthentication());

        CarConfirmBooking booking = carConfirmBookingRepository.findById(carConfirmBookingId).orElseThrow(() -> new ConfirmBookingNotFoundException("Confirm Booking not found"));
        Car car = booking.getCarCar();


        List<TempPendingBookingReq> backups = tempPendingBookingReqRepository.findByCarConfirmBookingId(carConfirmBookingId);
        System.out.println("Backups found: " + backups.size());
        for(TempPendingBookingReq backup : backups) {
            if (Duration.between(backup.getCreatedAt(), LocalDateTime.now()).toDays() <= 30) {

                User user = userRepository.findById(backup.getUserId()).orElseThrow(() -> new UserNotFoundExceptions("User not found"));

                Dealer dealer = dealerRepository.findById(backup.getDealerId()).orElseThrow(() -> new DealerNotFoundException("Dealer not found Exception"));

                PendingBooking restored = new PendingBooking();
                restored.setCar(car);
                restored.setDate(LocalDate.now());
                restored.setUser(user);
                restored.setDealer(dealer);
                restored.setAskingPrice(backup.getAskingPrice());
                restored.setPrice(backup.getPrice());
                restored.setStatus(Status.PENDING);
                pendingBookingRepository.save(restored);
                System.out.println("Restored PendingBooking for userId: " + user.getId());

            }else {
                tempPendingBookingReqRepository.delete(backup);
                System.out.println("Deleted backup older than 30 days for userId: " + backup.getUserId());
            }
        }

        tempPendingBookingReqRepository.deleteAll(backups);
        carConfirmBookingRepository.delete(booking);
        updateCarStatus(car.getId(), CarStatus.PENDING);

    }

    private void updateCarStatus(Integer carId, CarStatus carStatus)
    {
        Car car = carRepository.findById(carId).orElseThrow(() -> new CarNotFoundException("Car not Found"));

        car.setCarStatus(carStatus);
        carRepository.saveAndFlush(car);
    }


}
