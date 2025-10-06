package com.spring.jwt.CarView;

import com.spring.jwt.entity.Car;
import com.spring.jwt.entity.CarView;
import com.spring.jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarViewRepository extends JpaRepository<CarView , Integer> {

    Optional<CarView> findByUserIdAndCarId(Integer user_id, Integer car_id);
    //Optional<CarView> findByUserAndCar(User user, Car car);

}
