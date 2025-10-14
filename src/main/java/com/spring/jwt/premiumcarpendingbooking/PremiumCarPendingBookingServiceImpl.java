package com.spring.jwt.premiumcarpendingbooking;
import com.spring.jwt.PremiumCarData.PremiumCar;
import com.spring.jwt.PremiumCarData.PremiumCarNotFoundException;
import com.spring.jwt.PremiumCarData.PremiumCarRepository;
import com.spring.jwt.dealer.exception.DealerNotFoundException;
import com.spring.jwt.entity.Dealer;
import com.spring.jwt.entity.PremiumCarPendingBooking;
import com.spring.jwt.entity.Status;
import com.spring.jwt.entity.User;
import com.spring.jwt.exception.UserNotFoundExceptions;
import com.spring.jwt.repository.DealerRepository;
import com.spring.jwt.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PremiumCarPendingBookingServiceImpl implements PremiumCarPendingBookingService {
    private final PremiumCarPendingBookingRepository premiumcarRepository;
    private final PremiumCarConfirmBookingRepository premiumcarConfirmRepo;
    private final UserRepository userRepository;
    private final PremiumCarRepository premiumCarRepository;
    private final DealerRepository dealerRepository;
    @Override
    public PremiumCarPendingBooking createBooking(PremiumCarPendingBookingDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundExceptions("User not found with id " + dto.getUserId()));
        Dealer dealer = dealerRepository.findById(dto.getDealerId())
                .orElseThrow(() -> new DealerNotFoundException("Dealer not found with id " + dto.getDealerId()));
        PremiumCar premiumCar = premiumCarRepository.findById(dto.getPremiumCar())
                .orElseThrow(() -> new PremiumCarNotFoundException("PremiumCar not found with id " + dto.getPremiumCar()));
        PremiumCarPendingBooking entity = PremiumCarPendingBookingMapper.
                toEntity(dto, user, dealer,premiumCar);
        entity.setStatus(String.valueOf(Status.PENDING));
        premiumcarRepository.save(entity);
        return entity;
    }
    @Override
    public List<PremiumCarPendingBookingDto> getBookingByDealerId(Integer dealerId,int page,int size) {
        Dealer dealer = dealerRepository.findById(dealerId)
                .orElseThrow(() -> new DealerNotFoundException("Dealer not found with id " + dealerId));

        Pageable pageable = PageRequest.of(page,size);
        Page<PremiumCarPendingBooking> booking = premiumcarRepository.findByDealer(dealer,pageable);
       return booking.stream().map(PremiumCarPendingBookingMapper::toDto)
               .collect(Collectors.toList());
    }
    @Override
    public  List<PremiumCarPendingBookingDto>getBookingByUserId(Integer userId,int page,int size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundExceptions("User not found with id " + userId));
        Pageable pageable = PageRequest.of(page,size);
        Page<PremiumCarPendingBooking> booking = premiumcarRepository.findByUser(user,pageable);

        return booking.stream().map(PremiumCarPendingBookingMapper::toDto)
              .collect(Collectors.toList());

    }
    @Override
    public PremiumCarPendingBookingDto getByPendingBookingId(Long premiumCarPendingBookingId){
        PremiumCarPendingBooking booking = premiumcarRepository.findById(premiumCarPendingBookingId).
                    orElseThrow(()->new RuntimeException("Booking not found"));
        return PremiumCarPendingBookingMapper.toDto(booking);
    }
    @Override
    public List<PremiumCarPendingBookingDto> getAllPendingBooking(int page,int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<PremiumCarPendingBooking> booking = premiumcarRepository.findAll(pageable);
        return booking.stream().map(PremiumCarPendingBookingMapper::toDto)
                .collect(Collectors.toList());
    }
//    @Override
//    public void updateBookingStatus(Long bookingId, Integer dealerId, Status status) {
//
//        PremiumCarPendingBooking booking = repository.findById(bookingId).orElseThrow(()
//                -> new InvalidIdException("Pending booking not found with id " +
//                bookingId));
//        if (confirmRepo.existsByPendingBooking_PremiumCarPendingBookingId(bookingId)) {
//            throw new InvalidIdException("Duplicate ID: Booking already confirmed");
//        }
//        booking.setStatus(String.valueOf(status));
//        repository.save(booking);
//        if(status==Status.SOLD){
//            ConfirmBooking confirm = new ConfirmBooking();
//            confirm.setPendingBooking(booking);
//            confirm.setDealer(booking.getDealer());
//            confirm.setUser(booking.getUser());
//            confirm.setStatus(status);
//            confirm.setConfirmedAt(LocalDate.now());
//            confirmRepo.save(confirm);
//        }
//
//    }

    @PreAuthorize("hasRole('DEALER')")
    @Override
    public void updateBookingStatus(Long bookingId, @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email);
        if (user == null) {
          throw new InvalidIdException("User not found for email: " + email);
        }

        Dealer dealer = dealerRepository.findByUser(user)
                .orElseThrow(() -> new DealerNotFoundException("Dealer not found for user: " + email));


        PremiumCarPendingBooking booking = premiumcarRepository.findById(bookingId)
                .orElseThrow(() -> new InvalidIdException("Pending booking not found with id " + bookingId));

        if (premiumcarConfirmRepo.existsByPendingBooking_PremiumCarPendingBookingId(bookingId)) {
            throw new InvalidIdException("Duplicate ID: Booking already confirmed");
        }

        booking.setStatus(Status.SOLD.name());
        premiumcarRepository.save(booking);

        PremiumCarConfirmBooking confirm = new PremiumCarConfirmBooking();
        confirm.setPendingBooking(booking);
        confirm.setDealer(dealer);
        confirm.setUser(booking.getUser());
        confirm.setStatus(Status.SOLD);
        confirm.setConfirmedAt(LocalDate.now());
        premiumcarConfirmRepo.save(confirm);
    }

}