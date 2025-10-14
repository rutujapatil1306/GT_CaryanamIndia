package com.spring.jwt.premiumcarsconfirmbookings;
import com.spring.jwt.PremiumCarData.PremiumCar;
import com.spring.jwt.PremiumCarData.PremiumCarRepository;
import com.spring.jwt.entity.*;
import com.spring.jwt.premiumcarpendingbooking.PremiumCarConfirmBooking;
import com.spring.jwt.premiumcarpendingbooking.PremiumCarConfirmBookingRepository;
import com.spring.jwt.premiumcarpendingbooking.PremiumCarPendingBookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor
public class PremiumCarBookingServiceImpl implements PremiumCarBookingService {


    private final PremiumCarPendingBookingRepository premiumcarPendingRepo;
    private final PremiumCarConfirmBookingRepository premiumcarConfirmRepo;
    private final TempPendingBookingReqRepository tempPremiumcarRepo;
    private final PremiumCarRepository PremiumcarRepo;

    @Override
    @Transactional
    public ConfirmBookingDto confirmBooking(Long pendingBookingId) {
        // Find pending booking
        PremiumCarPendingBooking confirmedPending = premiumcarPendingRepo.findById(pendingBookingId)
                .orElseThrow(() -> new RuntimeException("Pending booking not found"));

        PremiumCar car = confirmedPending.getPremiumCarCar();

        if (car.getCarInsuranceDate() == null || car.getCarInsuranceDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Cannot confirm booking: Car insurance is missing or expired.");
        }


        Long carId = Long.valueOf(car.getPremiumCarId());


        // Get all pending bookings for that car
        List<PremiumCarPendingBooking> relatedPendingBookings =
                premiumcarPendingRepo.findByPremiumCarCar_PremiumCarId(carId);


        //  Move other pending bookings to temp table and delete them
        for (PremiumCarPendingBooking pending : relatedPendingBookings) {
            if (!pending.getPremiumCarPendingBookingId().equals(pendingBookingId)) {


                TempPendingBookingReq temp = new TempPendingBookingReq();
                temp.setDate(pending.getDate());
                temp.setPrice(pending.getPrice());
                temp.setDealerId(pending.getDealer().getId());
                temp.setUserId(pending.getUser().getId());
                temp.setStatus(Status.valueOf(pending.getStatus()));
                temp.setCreatedDate(LocalDateTime.now());
                temp.setPremiumCarCar(pending.getPremiumCarCar());
                temp.setAskingPrice(pending.getAskingPrice());
                tempPremiumcarRepo.save(temp);


                // Delete the pending booking
                premiumcarPendingRepo.delete(pending);
            }
        }


        //  Update confirmed pending booking status
        confirmedPending.setStatus(String.valueOf(Status.SOLD));
        premiumcarPendingRepo.save(confirmedPending);


        //  Create Confirm Booking record
        PremiumCarConfirmBooking confirm = new PremiumCarConfirmBooking();
        confirm.setPendingBooking(confirmedPending);
        confirm.setDealer(confirmedPending.getDealer());
        confirm.setUser(confirmedPending.getUser());
        confirm.setStatus(Status.SOLD);
        confirm.setConfirmedAt(LocalDate.now());
        premiumcarConfirmRepo.save(confirm);


        // Update car status
        car.setCarstatus(Status.SOLD);
        PremiumcarRepo.save(car);


        //  Prepare response DTO
        ConfirmBookingDto dto = new ConfirmBookingDto();
        dto.setConfirmId(confirm.getId());
        dto.setPremiumcarpendingbookingid(pendingBookingId);
        dto.setDealerId(confirm.getDealer().getId());
        dto.setUserId(confirm.getUser().getId());
        dto.setStatus(confirm.getStatus());
        dto.setConfirmAt(confirm.getConfirmedAt());

        return dto;
    }
    @Override
    @Transactional
    public String deleteConfirmedBooking(Long confirmBookingId) {
        PremiumCarConfirmBooking confirmBooking = premiumcarConfirmRepo.findById(confirmBookingId)
                .orElseThrow(() -> new RuntimeException("Premium Car Confirmed booking not found"));

        PremiumCar car = confirmBooking.getPendingBooking().getPremiumCarCar();
        Long premiumCarId = Long.valueOf(car.getPremiumCarId());

        List<TempPendingBookingReq> tempList = tempPremiumcarRepo.findByPremiumCarCar_PremiumCarId(premiumCarId);
        LocalDateTime now = LocalDateTime.now();
        for (TempPendingBookingReq temp : tempList) {
            if (temp.getCreatedDate().plusDays(30).isAfter(now)) {
                PremiumCarPendingBooking restored = new PremiumCarPendingBooking();
                restored.setDealer(confirmBooking.getDealer());
                restored.setUser(confirmBooking.getUser());
                restored.setStatus(String.valueOf(Status.PENDING));
                restored.setPrice(temp.getPrice());
                restored.setPremiumCarCar(car);
                restored.setAskingPrice(temp.getAskingPrice());
                restored.setDate(LocalDate.now());
                premiumcarPendingRepo.save(restored);
            }
            tempPremiumcarRepo.delete(temp);
        }

        car.setCarstatus(Status.PENDING);
        PremiumcarRepo.save(car);

        premiumcarConfirmRepo.delete(confirmBooking);
        return "Confirmed Booking deleted successfully";
    }
}

