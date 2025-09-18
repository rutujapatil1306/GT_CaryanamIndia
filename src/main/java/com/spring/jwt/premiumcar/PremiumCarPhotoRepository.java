package com.spring.jwt.premiumcar;

import com.spring.jwt.premiumcar.PremiumCarPhoto;
import com.spring.jwt.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PremiumCarPhotoRepository extends JpaRepository<PremiumCarPhoto, Long> {

    List<PremiumCarPhoto> findByCar(Car car);

}
