package com.spring.jwt.CarPhoto;

import com.spring.jwt.entity.Car;
import com.spring.jwt.entity.CarPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarPhotoRepository extends JpaRepository<CarPhoto, Integer> {

        boolean existsByCarAndType(Car car, DocType type);

        boolean existsByCarAndHashAndIdNot(Car car, String hash, Integer id);

        Optional<CarPhoto> findByCarId(Integer carId);

        Optional<CarPhoto> findByCarAndType(Car car, DocType type);

        boolean existsByCarAndHash(Car car, String hash);

        boolean existsByCarAndTypeAndIdNot(Car car, DocType type, Integer id);


}
