package com.spring.jwt.premiumcar;

import com.spring.jwt.PremiumCarData.PremiumCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiumCarsRepository extends JpaRepository<PremiumCar, Integer> {
    // You can add custom queries here if needed
}

