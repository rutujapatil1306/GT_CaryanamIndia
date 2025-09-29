package com.spring.jwt.PremiumCarData;

import com.spring.jwt.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PremiumCarRepository extends JpaRepository<PremiumCar, Integer> {


    Page<PremiumCar> findByDealerIdAndCarstatus(Integer dealerId, Status status, Pageable pageable);


    Page<PremiumCar> findByDealerId(Integer dealerId, Pageable pageable);

    Page<PremiumCar> findByCarstatus(Status status, Pageable pageable);

    Optional<PremiumCar> findByMainCarId(String mainCarId);



}
