package com.spring.jwt.PremiumCarData;

import com.spring.jwt.entity.Status;
import org.springframework.data.domain.Page;

public interface PremiumService {
    PremiumCarDTO save(PremiumCarDTO premiumCarDTO);
    PremiumCarDTO update(PremiumCarDTO premiumCarDTO,Integer id);
    PremiumCarDTO findById(Integer id);
    PremiumCarDTO deleteById(Integer id);
     PremiumCarDTO softDelete(Integer id);
     PremiumCarDTO hardDelete(Integer id);
     Page<PremiumCarDTO> getCarsByDealerAndStatus(Integer dealerId, Status status, int page, int size, String sortBy);
 }


