package com.spring.jwt.CarTest;

import com.spring.jwt.Car.CarRepository;
import com.spring.jwt.Car.CarServiceImpl;
import com.spring.jwt.Car.DTO.CarDto;
import com.spring.jwt.Car.Exception.CarNotFoundException;
import com.spring.jwt.CarView.CarViewMapper;
import com.spring.jwt.CarView.CarViewRepository;

import com.spring.jwt.CarView.CarViewServiceImpl;
import com.spring.jwt.CarView.DTO.CarViewDto;
import com.spring.jwt.entity.Car;
import com.spring.jwt.entity.CarView;
import com.spring.jwt.entity.User;
import com.spring.jwt.exception.UserNotFoundExceptions;
import com.spring.jwt.repository.UserRepository;
//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CarServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarViewRepository carViewRepository;

    @Mock
    private CarViewMapper carViewMapper;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    public void testGetCarById_NewCarView()
    {
        Integer userId = 1;
        Integer carId = 2;

        User user = new User();
        user.setId(userId);

        Car car = new Car();
        car.setId(carId);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        Mockito.when(carViewRepository.findByUserIdAndCarId(userId, carId)).thenReturn(Optional.empty());

        CarView savedCarView = new CarView();
        savedCarView.setUser(user);
        savedCarView.setCar(car);
        savedCarView.setCount(1);
        savedCarView.setLastViewedAt(LocalDateTime.now());

        Mockito.when(carViewRepository.save(Mockito.any(CarView.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Mockito.when(carViewMapper.toDto(Mockito.any(CarView.class))).
                thenAnswer(invocation -> {

                            CarView view = invocation.getArgument(0);
                            CarViewDto carViewDto = new CarViewDto();
                            carViewDto.setUser_id(view.getUser().getId());
                            carViewDto.setCar_id(view.getCar().getId());
                            carViewDto.setCount(view.getCount());
                            carViewDto.setLastViewedAt(view.getLastViewedAt());
                            return carViewDto;
                        });


        CarDto result = carService.getCarById(userId, carId);

        Mockito.verify(userRepository).findById(userId);
        Mockito.verify(carRepository).findById(carId);
        Mockito.verify(carViewRepository).findByUserIdAndCarId(userId, carId);
        Mockito.verify(carViewRepository).save(Mockito.any(CarView.class));
        Mockito.verify(carViewMapper).toDto(Mockito.any(CarView.class));

        Assertions.assertAll("CarDto values",
                () -> Assertions.assertEquals(userId, result.getUser_id()),
                () -> Assertions.assertEquals(carId, result.getCar_id()),
                () -> Assertions.assertEquals(1, result.getCount()),
                () -> Assertions.assertNotNull(result.getLastViewedAt())
        );
        System.out.println("Test passed: testSaveUserCarView_NewCarView");

    }

    @Test
    public void testSaveUserCarView_ExistingCarView() {
        Integer userId = 1;
        Integer carId = 2;

        User user = new User();
        user.setId(userId);

        Car car = new Car();
        car.setId(carId);

        // viewcount in DB with count = 3
        CarView existingCarView = new CarView();
        existingCarView.setUser(user);
        existingCarView.setCar(car);
        existingCarView.setCount(3);
        existingCarView.setLastViewedAt(LocalDateTime.now());

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        Mockito.when(carViewRepository.findByUserIdAndCarId(userId, carId))
                .thenReturn(Optional.of(existingCarView));

        Mockito.when(carViewRepository.save(Mockito.any(CarView.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Mockito.when(carViewMapper.toDto(Mockito.any(CarView.class)))
                .thenAnswer(invocation -> {
                    CarView view = invocation.getArgument(0);
                    CarViewDto dto = new CarViewDto();
                    dto.setUser_id(view.getUser().getId());
                    dto.setCar_id(view.getCar().getId());
                    dto.setCount(view.getCount());
                    dto.setLastViewedAt(view.getLastViewedAt());
                    return dto;
                });


        CarViewDto result = carViewService.saveUserCarView(userId, carId);


        Mockito.verify(userRepository).findById(userId);
        Mockito.verify(carRepository).findById(carId);
        Mockito.verify(carViewRepository).findByUserIdAndCarId(userId, carId);
        Mockito.verify(carViewRepository).save(Mockito.any(CarView.class));
        Mockito.verify(carViewMapper).toDto(Mockito.any(CarView.class));


        Assertions.assertAll("CarViewDto values",
                () -> Assertions.assertEquals(userId, result.getUser_id()),
                () -> Assertions.assertEquals(carId, result.getCar_id()),
                () -> Assertions.assertEquals(4, result.getCount()), // viewcount incremented from 3 to 4
                () -> Assertions.assertNotNull(result.getLastViewedAt())
        );

        System.out.println("Test passed: testSaveUserCarView_ExistingCarView");
    }

    @Test
    public void testGetCarViewCountByUser()
    {
        Integer userId = 1;
        Integer carId = 2;

        User user = new User();
        user.setId(userId);

        Car car = new Car();
        car.setId(carId);

        CarView view = new CarView();
        view.setUser(user);
        view.setCar(car);
        view.setCount(2);
        view.setLastViewedAt(LocalDateTime.now());

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        Mockito.when(carViewRepository.findByUserIdAndCarId(userId, carId));


    }





    @Test
    public void testSaveUserCarView_UserNotFound()
    {
       Integer userId = 1;
       Integer carId = 2;

       Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

       UserNotFoundExceptions exception = Assertions.assertThrows(UserNotFoundExceptions.class, () -> carViewService.saveUserCarView(userId, carId));

       Assertions.assertEquals("User Not Found At given Id: " +  userId, exception.getMessage());

       Mockito.verify(userRepository).findById(userId);
    }

    @Test
    public void testSaveUserCarView_CarNotFound()
    {
        Integer userId = 1;
        Integer carId = 2;

        User user = new User();
        user.setId(userId);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(carRepository.findById(carId)).thenReturn(Optional.empty());

        CarNotFoundException ex = Assertions.assertThrows(CarNotFoundException.class, () -> carViewService.saveUserCarView(userId, carId));

        Assertions.assertEquals("Car Not Found At id: " + carId, ex.getMessage());
        Mockito.verify(carRepository).findById(carId);
    }


}
