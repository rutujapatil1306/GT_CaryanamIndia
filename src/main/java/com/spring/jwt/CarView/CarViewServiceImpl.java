package com.spring.jwt.CarView;

import com.spring.jwt.Car.CarRepository;
import com.spring.jwt.Car.Exception.CarNotFoundException;
import com.spring.jwt.CarView.DTO.CarViewDto;
import com.spring.jwt.entity.Car;
import com.spring.jwt.entity.CarView;
import com.spring.jwt.entity.User;
import com.spring.jwt.exception.UserNotFoundExceptions;
import com.spring.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CarViewServiceImpl implements CarViewService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarViewRepository carViewRepository;

    @Autowired
    CarViewMapper carViewMapper;

    @Override
    public CarViewDto saveUserCarView(Integer user_id, Integer car_id) {

//        User user = userRepository.findAll().stream()
//                .filter(u -> u.getId().equals(user_id))
//                .findFirst()
//                .orElseThrow(() -> new UserNotFoundExceptions(
//                        "User not found at given id: " + user_id));

        User user = userRepository.findById(user_id).orElseThrow(() -> new UserNotFoundExceptions("User Not Found At given Id: " + user_id));

        Car car = carRepository.findById(car_id).orElseThrow(() -> new CarNotFoundException("Car Not Found At id: " + car_id));

        CarView carView = carViewRepository.findByUserIdAndCarId(user_id, car_id).orElseGet(() -> {
                    CarView view = new CarView();
                    view.setUser(user);
                    view.setCar(car);
                    view.setCount(0);

                    return view;
                }
        );

        carView.setCount(carView.getCount() + 1);
        carView.setLastViewedAt(LocalDateTime.now());

        CarView saved = carViewRepository.save(carView);

        return carViewMapper.toDto(saved);
    }

    @Override
    public CarViewDto getCarViewCountByUser(Integer user_id, Integer car_id) {

        User user = userRepository.findById(user_id).orElseThrow(() -> new UserNotFoundExceptions("User Not Found At given Id: " + user_id));

        Car car = carRepository.findById(car_id).orElseThrow(() -> new CarNotFoundException("Car Not found at Id: " + car_id));

        CarView carView = carViewRepository.findByUserIdAndCarId(user_id, car_id).orElse(null);

        //int count = carView != null ? carView.getCount() : 0 ;

        if(carView != null){
            return carViewMapper.toDto(carView);
        }
        else {
            CarViewDto dto = new CarViewDto();
            //dto.setId(carView.getId());
            dto.setUser_id(user_id);
            dto.setCar_id(car_id);
            dto.setCount(0);
            dto.setLastViewedAt(null);
            return dto;
        }
    }
}
