package com.spring.jwt.PendingBookingTest;
import com.spring.jwt.Car.CarRepository;
import com.spring.jwt.entity.Car;
import com.spring.jwt.entity.Dealer;
import com.spring.jwt.entity.PendingBooking;
import com.spring.jwt.entity.Status;
import com.spring.jwt.entity.User;
import com.spring.jwt.exception.PageNotFoundException;
import com.spring.jwt.pendingbooking.DTO.PendingBookingDTO;
import com.spring.jwt.pendingbooking.Exception.PendingBookingNotFoundException;
import com.spring.jwt.pendingbooking.PendingBookingMapper;
import com.spring.jwt.pendingbooking.PendingBookingRepository;
import com.spring.jwt.pendingbooking.PendingBookingServiceImpl;
import com.spring.jwt.premiumcar.ApiResponseDto;
import com.spring.jwt.repository.DealerRepository;
import com.spring.jwt.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PendingBookingServiceImplTest {
    @Mock
    private PendingBookingRepository repository;
    @Mock
    private DealerRepository dealerRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CarRepository carRepository;
    @Mock
    private PendingBookingMapper mapper;
    @InjectMocks
    private PendingBookingServiceImpl service;

    @Test
    public void testCreatePendingBookingSuccess() {
        Integer dealerId = 1;
        Integer userId = 1;
        Integer carId = 1;

        Dealer dealer = new Dealer();
        dealer.setId(dealerId);

        User user = new User();
        user.setId(userId);

        Car car = new Car();
        car.setId(carId);

        PendingBookingDTO dto = PendingBookingDTO.builder()
                .id(1)
                .date(LocalDate.now())
                .price(1000)
                .askingPrice(1200)
                .dealerId(dealerId)
                .userId(userId)
                .carId(carId)
                .status(String.valueOf(Status.PENDING))
                .build();

        PendingBooking entity = new PendingBooking();
        entity.setId(dto.getId());
        entity.setDate(dto.getDate());
        entity.setPrice(dto.getPrice());
        entity.setAskingPrice(dto.getAskingPrice());
        entity.setDealer(dealer);
        entity.setUser(user);
        entity.setCar(car);
        entity.setStatus(Status.valueOf(dto.getStatus()));

        // Mocking repository and mapper behavior
        Mockito.when(dealerRepository.findById(dealerId)).thenReturn(Optional.of(dealer));
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        Mockito.when(mapper.toEntity(dto, dealer, user, car)).thenReturn(entity);
        Mockito.when(repository.save(any(PendingBooking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ApiResponseDto response = service.create(dto);

        // Verifications
        Mockito.verify(dealerRepository).findById(dealerId);
        Mockito.verify(userRepository).findById(userId);
        Mockito.verify(carRepository).findById(carId);
        Mockito.verify(mapper).toEntity(dto, dealer, user, car);
        Mockito.verify(repository).save(entity);

        // Assertions
        Assertions.assertEquals("success", response.getStatus());
        Assertions.assertEquals(201, response.getCode());
    }

    @Test
    public void testGetByIdSuccess() {
        Integer id = 1;

        Dealer dealer = new Dealer();
        dealer.setId(1);
        User user = new User();
        user.setId(1);
        Car car = new Car();
        car.setId(1);

        PendingBooking entity = new PendingBooking();
        entity.setId(id);
        entity.setDealer(dealer);
        entity.setUser(user);
        entity.setCar(car);
        entity.setStatus(Status.PENDING);
        entity.setPrice(1000);
        entity.setAskingPrice(1200);
        entity.setDate(LocalDate.now());

        PendingBookingDTO dto = PendingBookingDTO.builder()
                .id(id)
                .dealerId(1)
                .userId(1)
                .carId(1)
                .status(String.valueOf(Status.PENDING))
                .price(1000)
                .askingPrice(1200)
                .date(LocalDate.now())
                .build();

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(entity));
        Mockito.when(mapper.toDTO(entity)).thenReturn(dto);

        PendingBookingDTO result = service.getById(id);

        Mockito.verify(repository).findById(id);
        Mockito.verify(mapper).toDTO(entity);

        Assertions.assertEquals(id, result.getId());
        Assertions.assertEquals(1, result.getDealerId());
        Assertions.assertEquals(1, result.getUserId());
        Assertions.assertEquals(1, result.getCarId());
    }

    @Test
    public void testUpdatePendingBookingSuccess() {
        Integer id = 1;
        Dealer dealer = new Dealer(); dealer.setId(1);
        User user = new User(); user.setId(1);
        Car car = new Car(); car.setId(1);

        PendingBookingDTO dto = PendingBookingDTO.builder()
                .dealerId(1)
                .userId(1)
                .carId(1)
                .status(String.valueOf(Status.PENDING))
                .price(2000)
                .askingPrice(2200)
                .date(LocalDate.now())
                .build();

        PendingBooking entity = new PendingBooking();
        entity.setId(id);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(entity));
        Mockito.when(dealerRepository.findById(1)).thenReturn(Optional.of(dealer));
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(carRepository.findById(1)).thenReturn(Optional.of(car));
        Mockito.when(repository.save(entity)).thenReturn(entity);

        ApiResponseDto response = service.update(id, dto);

        Mockito.verify(repository).findById(id);
        Mockito.verify(repository).save(entity);

        Assertions.assertEquals("success", response.getStatus());
        Assertions.assertEquals(200, response.getCode());
    }

    @Test
    public void testDeletePendingBookingSuccess() {
        Integer id = 1;

        Mockito.when(repository.existsById(id)).thenReturn(true);
        Mockito.doNothing().when(repository).deleteById(id);

        service.delete(id);

        Mockito.verify(repository).existsById(id);
        Mockito.verify(repository).deleteById(id);
    }

    @Test
    public void testDeletePendingBookingNotFound() {
        Integer id = 1;

        Mockito.when(repository.existsById(id)).thenReturn(false);

        Assertions.assertThrows(RuntimeException.class, () -> service.delete(id));

        Mockito.verify(repository).existsById(id);
    }

    @Test
    public void testGetPendingBookingById_Success(){
        PendingBooking booking = new PendingBooking();
        User user = new User();
        Dealer dealer = new Dealer();
        Car car = new Car();
        booking.setId(1);
        booking.setUser(user);
        booking.setDealer(dealer);
        booking.setDate(LocalDate.now());
        booking.setStatus(Status.PENDING);
        booking.setPrice(100);
        booking.setAskingPrice(85);
        booking.setCar(car);

        PendingBookingDTO dto = new PendingBookingDTO();
        dto.setId(1);
        dto.setUserId(user.getId());
        dto.setDealerId(dealer.getId());
        dto.setDate(LocalDate.now());
        dto.setStatus("PENDING");
        dto.setPrice(100);
        dto.setAskingPrice(85);
        dto.setCarId(car.getId());


        Mockito.when(repository.findById(1)).thenReturn(Optional.of(booking));
        Mockito.when(mapper.toDTO(booking)).thenReturn(dto);

        PendingBookingDTO result = service.getPendingBookingById(1);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getId());
        Assertions.assertEquals("PENDING", result.getStatus());
        Assertions.assertEquals(100, result.getPrice());
        Assertions.assertEquals(85, result.getAskingPrice());

        Mockito.verify(repository).findById(1);
        Mockito.verify(mapper).toDTO(booking);

    }

    @Test
    public void testGetPendingBookingById_NotFound(){

        PendingBooking booking = new PendingBooking();
        User user = new User();
        Dealer dealer = new Dealer();
        Car car = new Car();
        booking.setId(1);
        booking.setUser(user);
        booking.setDealer(dealer);
        booking.setDate(LocalDate.now());
        booking.setStatus(Status.PENDING);
        booking.setPrice(100);
        booking.setAskingPrice(85);
        booking.setCar(car);

        PendingBookingDTO dto = new PendingBookingDTO();
        dto.setId(1);
        dto.setUserId(user.getId());
        dto.setDealerId(dealer.getId());
        dto.setDate(LocalDate.now());
        dto.setStatus("PENDING");
        dto.setPrice(100);
        dto.setAskingPrice(85);
        dto.setCarId(car.getId());

        Mockito.when(repository.findById(99)).thenReturn(Optional.empty());

        PendingBookingNotFoundException exception = Assertions.assertThrows(PendingBookingNotFoundException.class, () -> service.getPendingBookingById(99));

        Assertions.assertEquals("Pending booking not Found At id: 99", exception.getMessage());
        Mockito.verify(repository).findById(99);
        Mockito.verify(mapper, Mockito.never()).toDTO(any());
    }

    @Test
    void testGetPendingBookingByDealerId_Success(){

        User user = new User();
        Dealer dealer = new Dealer();
        dealer.setId(1);
        dealer.setFirstname("Test Dealer");
        Car car = new Car();

        PendingBooking booking = new PendingBooking();
        booking.setId(1);
        booking.setUser(user);
        booking.setDealer(dealer);
        booking.setDate(LocalDate.now());
        booking.setStatus(Status.PENDING);
        booking.setPrice(100);
        booking.setAskingPrice(85);
        booking.setCar(car);

        PendingBookingDTO dto = new PendingBookingDTO();
        dto.setId(1);
        dto.setUserId(user.getId());
        dto.setDealerId(dealer.getId());
        dto.setDate(LocalDate.now());
        dto.setStatus("PENDING");
        dto.setPrice(100);
        dto.setAskingPrice(85);
        dto.setCarId(car.getId());

        Integer dealerId = 1;
        int page = 0;
        int size = 5;

        List<PendingBooking> bookings = List.of(booking);

        Mockito.when(dealerRepository.findById(dealerId)).thenReturn(Optional.of(dealer));
        Mockito.when(repository.countByDealerId(dealerId)).thenReturn(5L);
        Mockito.when(repository.findByDealer_Id(eq(dealerId),any(Pageable.class))).thenReturn(bookings);
        Mockito.when(mapper.toDTO(any(PendingBooking.class))).thenReturn(dto);

        List<PendingBookingDTO> result = service.getPendingBookingByDealerId(dealerId, page, size);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(1, result.get(0).getId());

        Mockito.verify(dealerRepository, Mockito.times(1)).findById(dealerId);
        Mockito.verify(repository, Mockito.times(1)).countByDealerId(dealerId);
        Mockito.verify(repository, Mockito.times(1)).findByDealer_Id(eq(dealerId),any(Pageable.class));
    }

    @Test
    void testGetPendingBookingByDealerId_PendingBookingNotFound(){
        Dealer dealer = new Dealer();
        dealer.setId(1);
        dealer.setFirstname("Test Dealer");

        Integer dealerId = 1;
        int page = 0;
        int size = 5;


        Mockito.when(dealerRepository.findById(dealerId)).thenReturn(Optional.of(dealer));
        Mockito.when(repository.countByDealerId(dealerId)).thenReturn(5L);
        Mockito.when(repository.findByDealer_Id(eq(dealerId), any(Pageable.class))).thenReturn(List.of());

        Assertions.assertThrows(PendingBookingNotFoundException.class, () -> service.getPendingBookingByDealerId(dealerId, page, size));
        Mockito.verify(dealerRepository, Mockito.times(1)).findById(dealerId);
        Mockito.verify(repository, Mockito.times(1)).countByDealerId(dealerId);

        Mockito.verify(repository, Mockito.times(1)).findByDealer_Id(eq(dealerId),any(Pageable.class));
    }

    @Test
    void testGetPendingBookingByDealerId_PageNotFound(){
        Dealer dealer = new Dealer();
        dealer.setId(1);
        dealer.setFirstname("Test Dealer");

        Integer dealerId = 1;
        int page = 5;
        int size = 5;

        Mockito.when(dealerRepository.findById(dealerId)).thenReturn(Optional.of(dealer));
        Mockito.when(repository.countByDealerId(dealerId)).thenReturn(10L);

        Assertions.assertThrows(PageNotFoundException.class, () -> service.getPendingBookingByDealerId(dealerId, page, size));

        Mockito.verify(dealerRepository, Mockito.times(1)).findById(dealerId);
        Mockito.verify(repository, Mockito.times(1)).countByDealerId(dealerId);
        Mockito.verify(repository, Mockito.never()).findByDealer_Id((int) anyLong(),any(Pageable.class));

    }

    @Test
    void getPendingBookingByUserId_Success(){

        User user = new User();
        user.setId(1);
        user.setFirstName("Test User");
        Dealer dealer = new Dealer();
        Car car = new Car();


        Integer userId = 1;
        int page = 0;
        int size = 5;

        PendingBooking booking = new PendingBooking();
        booking.setId(1);
        booking.setUser(user);
        booking.setDealer(dealer);
        booking.setDate(LocalDate.now());
        booking.setStatus(Status.PENDING);
        booking.setPrice(100);
        booking.setAskingPrice(85);
        booking.setCar(car);

        PendingBookingDTO dto = new PendingBookingDTO();
        dto.setId(1);
        dto.setUserId(user.getId());
        dto.setDealerId(dealer.getId());
        dto.setDate(LocalDate.now());
        dto.setStatus("PENDING");
        dto.setPrice(100);
        dto.setAskingPrice(85);
        dto.setCarId(car.getId());

        List<PendingBooking> bookings = List.of(booking);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(repository.countByUserId(userId)).thenReturn(5L);
        Mockito.when(repository.findByUser_Id(eq(userId), any(Pageable.class))).thenReturn(bookings);
        Mockito.when(mapper.toDTO(any(PendingBooking.class))).thenReturn(dto);


        List<PendingBookingDTO> result = service.getPendingBookingByUserId(userId, page, size);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(1, result.get(0).getId());

        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
        Mockito.verify(repository, Mockito.times(1)).countByUserId(userId);
        Mockito.verify(repository, Mockito.times(1)).findByUser_Id(eq(userId),any(Pageable.class));
    }

    @Test
    void getPendingBookingByUserId_PendingBookingNotFound(){
        User user = new User();
        user.setId(1);
        user.setFirstName("Test User");

        Integer userId = 1;
        int page = 0;
        int size = 5;


        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(repository.countByUserId(userId)).thenReturn(5L);
        Mockito.when(repository.findByUser_Id(eq(userId), any(Pageable.class))).thenReturn(List.of());

        Assertions.assertThrows(PendingBookingNotFoundException.class, () -> service.getPendingBookingByUserId(userId, page, size));
        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
        Mockito.verify(repository, Mockito.times(1)).countByUserId(userId);

        Mockito.verify(repository, Mockito.times(1)).findByUser_Id(eq(userId),any(Pageable.class));

    }

    @Test
    void testGetPendingBookingByUserId_PageNotFound(){
        User user = new User();
        user.setId(1);
        user.setFirstName("Test User");

        Integer userId = 1;
        int page = 5;
        int size = 5;

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(repository.countByUserId(userId)).thenReturn(10L);

        Assertions.assertThrows(PageNotFoundException.class, () -> service.getPendingBookingByUserId(userId, page, size));

        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
        Mockito.verify(repository, Mockito.times(1)).countByUserId(userId);
        Mockito.verify(repository, Mockito.never()).findByUser_Id((int) anyLong(),any(Pageable.class));

    }






}

