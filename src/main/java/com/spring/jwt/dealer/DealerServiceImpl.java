package com.spring.jwt.dealer;
import com.spring.jwt.dealer.DTO.DealerResponseDto;
import com.spring.jwt.dealer.exception.DealerNotFoundException;
import com.spring.jwt.dto.DealerDTO;
import com.spring.jwt.entity.Dealer;
import com.spring.jwt.repository.DealerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealerServiceImpl implements DealerService {


    private final DealerRepository dealerRepository;



    @Override
    public DealerResponseDto updateDealer(Integer dealerId, DealerDTO dealerDTO) {
        Dealer dealer = dealerRepository.findById(dealerId)
                .orElseThrow(() -> new DealerNotFoundException("Dealer not found with id: " + dealerId));

        // Map DTO → Entity (you can make a helper method in DealerMapper)
        DealerMapper.updateEntityFromDTO(dealerDTO, dealer);

        Dealer updatedDealer = dealerRepository.save(dealer);
        DealerDTO dto = DealerMapper.toDTO(updatedDealer);

        return DealerResponseDto.success("Dealer updated successfully", dto);
    }

    @Override
    public DealerResponseDto deleteDealer(Integer dealerId) {
        Dealer dealer = dealerRepository.findById(dealerId)
                .orElseThrow(() -> new DealerNotFoundException("Dealer not found with id: " + dealerId));

        dealerRepository.delete(dealer);
        return DealerResponseDto.success("Dealer deleted successfully", null);
    }

    @Override
    public DealerResponseDto getDealerById(Integer dealerId) {
        Dealer dealer = dealerRepository.findById(dealerId)
                .orElseThrow(() -> new DealerNotFoundException("Dealer not found with id: " + dealerId));

        DealerDTO dto = DealerMapper.toDTO(dealer);
        return DealerResponseDto.success("Dealer fetched successfully", dto);
    }


    @Override
    public DealerResponseDto getAllDealers() {
        List<Dealer> dealers = dealerRepository.findAll();

        List<DealerDTO> dealerDTOs = dealers.stream()
                .map(DealerMapper::toDTO)
                .toList();

        return DealerResponseDto.successWithCount("Dealers fetched successfully", dealerDTOs.size());
    }




    @Override
    public DealerResponseDto getDealersByStatus(DealerStatus status) {
        List<DealerDTO> dealerDTOs = dealerRepository.findAll().stream()
                .filter(d -> d.getStatus() != null && d.getStatus().equals(status))
                .map(DealerMapper::toDTO) // ✅ mapping inside service
                .toList();

        return DealerResponseDto.successWithCount("Dealers fetched successfully", dealerDTOs.size());
    }


    @Override
    public DealerResponseDto getDealersByUserId(Long userId) {
        return dealerRepository.findAll().stream()
                .filter(d -> d.getUser() != null && d.getUser().getId().equals(userId.intValue()))
                .findFirst()
                .map(dealer -> DealerResponseDto.success("Dealer fetched successfully", DealerMapper.toDTO(dealer)))
                .orElseThrow(() -> new DealerNotFoundException("No dealer found for userId: " + userId));
    }




    @Override
    public Page<Dealer> getDealersWithPagination(Pageable pageable)
    {
        return dealerRepository.findAll(pageable);
    }


    @Override
    public DealerResponseDto updateDealerStatus(Integer dealerId, DealerStatus status) {
        Dealer dealer = dealerRepository.findById(dealerId)
                .orElseThrow(() -> new DealerNotFoundException("Dealer not found with id: " + dealerId));

        dealer.setStatus(status);
        Dealer savedDealer = dealerRepository.save(dealer);

        DealerDTO dto = DealerMapper.toDTO(savedDealer);
        return DealerResponseDto.success("Dealer status updated successfully", dto);
    }

    // Sorting Logic
    @Override
    public DealerResponseDto getDealersWithPagination(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Dealer> dealerPage = dealerRepository.findAll(pageRequest);

        List<DealerDTO> dealerDTOs = dealerPage.getContent()
                .stream()
                .map(DealerMapper::toDTO)
                .toList();

        return DealerResponseDto.successWithCount("Dealers fetched successfully", dealerDTOs.size());
    }



}

