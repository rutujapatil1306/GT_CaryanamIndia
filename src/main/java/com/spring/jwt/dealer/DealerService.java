package com.spring.jwt.dealer;

import com.spring.jwt.dealer.DealerStatus;
import com.spring.jwt.dto.DealerDTO;
import com.spring.jwt.entity.Dealer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DealerService {
    Dealer updateDealer(Integer dealerId, DealerDTO dealerDTO);
    void deleteDealer(Integer dealerId);
    Dealer getDealerById(Integer dealerId);
    List<Dealer> getAllDealers();
    List<Dealer> getDealersByStatus(DealerStatus status);
    List<Dealer> getDealersByUserId(Long userId);
    Page<Dealer> getDealersWithPagination(Pageable pageable);

    Dealer updateDealerStatus(Integer dealerId, DealerStatus status);
    Page<Dealer> getDealersWithPagination(int page, int size, String sortBy, String sortDir);

}

