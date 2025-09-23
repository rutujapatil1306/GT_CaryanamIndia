package com.spring.jwt.PremiumCarData;

import com.spring.jwt.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PremiumCarService {
     PremiumCarDTO createPremiumCar(PremiumCarDTO dto);
   PremiumCarDTO updatePremiumCar(Integer id, PremiumCarDTO dto);
    PremiumCarDTO getPremiumCarById(Integer id);
    void deletePremiumCar(Integer carId, String type);

    Page<PremiumCarDTO> getCarsByDealerAndStatus(Long dealerId, Status status, Pageable pageable);
    Page<PremiumCarDTO> getCarsByStatus(Status status, int page, int size, String sortBy, String sortDir);

    PremiumCarDTO getCarByMainCarId(String mainCarId);

}
