package com.spring.jwt.CarPhoto;

import com.spring.jwt.entity.Car;
import com.spring.jwt.entity.CarPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarPhotoRepository extends JpaRepository<CarPhoto, Integer> {

        boolean existsByCarAndType(Car car, DocType type);

        Optional<CarPhoto> findByCarId(Integer carId);

}
