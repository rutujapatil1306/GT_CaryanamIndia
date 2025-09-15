package com.spring.jwt.PremiumCarData;

import com.spring.jwt.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PremiumCarRepository extends JpaRepository<PremiumCar, Integer> {
    Page<PremiumCar> findByDealerIdAndCarStatus(Integer dealerId, Status status, Pageable pageable);
}
