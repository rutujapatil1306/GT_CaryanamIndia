package com.spring.jwt.dealer;
import com.spring.jwt.dealer.DealerStatus;
import com.spring.jwt.dto.DealerDTO;
import com.spring.jwt.entity.Dealer;
import com.spring.jwt.dealer.DealerNotFoundException;
import com.spring.jwt.dealer.InvalidDealerDataException;
import com.spring.jwt.repository.DealerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealerServiceImpl implements DealerService {


    private final DealerRepository dealerRepository;



    @Override
    public Dealer updateDealer(Integer dealerId, DealerDTO dealerDTO) {
        Dealer dealer = dealerRepository.findById(dealerId)
                .orElseThrow(() -> new DealerNotFoundException("Dealer not found with id: " + dealerId));

        DealerMapper.updateEntityFromDTO(dealerDTO, dealer);
        return dealerRepository.save(dealer);
    }

    @Override
    public void deleteDealer(Integer dealerId) {
        if (!dealerRepository.existsById(dealerId)) {
            throw new DealerNotFoundException("Dealer not found with id: " + dealerId);
        }
        dealerRepository.deleteById(dealerId);
    }

    @Override
    public Dealer getDealerById(Integer dealerId) {
        return dealerRepository.findById(dealerId)
                .orElseThrow(() -> new DealerNotFoundException("Dealer not found with id: " + dealerId));
    }

    @Override
    public List<Dealer> getAllDealers() {
        return dealerRepository.findAll();
    }


    public List<Dealer> getDealersByStatus(DealerStatus status) {
        return dealerRepository.findAll().stream()
                .filter(d -> d.getStatus() != null && d.getStatus().equals(status))
                .toList();
    }

    @Override
    public List<Dealer> getDealersByUserId(Long userId) {
        return dealerRepository.findAll().stream()
                .filter(d -> d.getUser() != null && d.getUser().getId().equals(userId.intValue()))
                .toList();
    }


    @Override
    public Page<Dealer> getDealersWithPagination(Pageable pageable)
    {
        return dealerRepository.findAll(pageable);
    }


    @Override
    public Dealer updateDealerStatus(Integer dealerId, DealerStatus status) {
        Dealer dealer = dealerRepository.findById(dealerId)
                .orElseThrow(() -> new DealerNotFoundException("Dealer not found with id: " + dealerId));

        dealer.setStatus(status);
        return dealerRepository.save(dealer);
    }
    // Sorting Logic
    @Override
    public Page<Dealer> getDealersWithPagination(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return dealerRepository.findAll(pageRequest);
    }

}

