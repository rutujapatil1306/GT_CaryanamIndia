package com.spring.jwt.premiumcar;

import com.spring.jwt.PremiumCarData.PremiumCar;
import com.spring.jwt.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PremiumCarPhotoRepository extends JpaRepository<PremiumCarPhoto, Long> {

    List<PremiumCarPhoto> findByPremiumCar(PremiumCar premiumCar);

    List<PremiumCarPhoto> findByPremiumCarAndDocType(PremiumCar premiumCar, DocType docType);

    void deleteByPremiumCar(PremiumCar premiumCar);

    boolean existsByPremiumCarAndDocType(PremiumCar premiumCar, DocType docType);



}
