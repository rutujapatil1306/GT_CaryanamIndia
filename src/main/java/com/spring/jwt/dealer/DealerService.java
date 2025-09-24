package com.spring.jwt.dealer;

import com.spring.jwt.dealer.DTO.DealerResponseDto;
import com.spring.jwt.dealer.DealerStatus;
import com.spring.jwt.dto.DealerDTO;
import com.spring.jwt.entity.Dealer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.spring.jwt.entity.User;
import java.util.List;

public interface DealerService {

    DealerResponseDto updateDealer(Integer dealerId, DealerDTO dealerDTO);

    DealerResponseDto deleteDealer(Integer dealerId);

    DealerResponseDto getDealerById(Integer dealerId);
    DealerResponseDto getAllDealers();
    DealerResponseDto getDealersByStatus(DealerStatus status);
    DealerResponseDto getDealersByUserId(Long userId);

    DealerResponseDto updateDealerStatus(Integer dealerId, DealerStatus status);
    DealerResponseDto getDealersWithPagination(int page, int size, String sortBy, String sortDir);

}

