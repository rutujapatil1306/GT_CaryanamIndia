package com.spring.jwt.dealer;
import com.spring.jwt.dealer.DTO.DealerResponseDto;
import com.spring.jwt.dealer.exception.DealerNotFoundException;
import com.spring.jwt.dto.DealerDTO;
import com.spring.jwt.entity.Dealer;
import com.spring.jwt.entity.User;
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
        if (dealerDTO.getFirstname() != null) {
            if (dealerDTO.getFirstname().isBlank()) {
                throw new IllegalArgumentException("Firstname cannot be blank");
            }
            dealer.setFirstname(dealerDTO.getFirstname());
        }

        if (dealerDTO.getAddress() != null) {
            if (dealerDTO.getAddress().isBlank()) {
                throw new IllegalArgumentException("Address cannot be blank");
            }
            dealer.setAddress(dealerDTO.getAddress());
        }
        if (dealerDTO.getShopName() != null) {
            if (dealerDTO.getShopName().isBlank()) {
                throw new IllegalArgumentException("Shop name cannot be blank");
            }
            dealer.setShopName(dealerDTO.getShopName());
        }
//        if (dealerDTO.getMobileNo() != null) {
//            if (dealerDTO.getMobileNo().isBlank()) {
//                throw new IllegalArgumentException("Mobile number cannot be blank");
//            }
//            if (!dealerDTO.getMobileNo().matches("\\d{10}")) {
//                throw new IllegalArgumentException("Mobile number must be 10 digits");
//            }
//            dealer.setMobileNo(dealerDTO.getMobileNo());
//        }
        if (dealerDTO.getLastName() != null) {
            if (dealerDTO.getLastName().isBlank()) {
                throw new IllegalArgumentException("Last name cannot be blank");
            }
            dealer.setLastName(dealerDTO.getLastName());
        }

        if (dealerDTO.getArea() != null) {
            if (dealerDTO.getArea().isBlank()) {
                throw new IllegalArgumentException("Area cannot be blank");
            }
            dealer.setArea(dealerDTO.getArea());
        }

        if (dealerDTO.getEmail() != null) {
            if (dealerDTO.getEmail().isBlank()) {
                throw new IllegalArgumentException("Email cannot be blank");
            }
            if (!dealerDTO.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                throw new IllegalArgumentException("Email format is invalid");
            }
            dealer.setEmail(dealerDTO.getEmail());
        }

        if (dealerDTO.getStatus() != null) {
            dealer.setStatus(dealerDTO.getStatus());
        } else {
            throw new IllegalArgumentException("Dealer status cannot be null");
        }
        // Map DTO → Entity (you can make a helper method in DealerMapper)
        DealerMapper.updateEntityFromDTO(dealerDTO, dealer);

        Dealer updatedDealer = dealerRepository.save(dealer);
        DealerDTO dto = DealerMapper.toDTO(updatedDealer, false);

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

        DealerDTO dto = DealerMapper.toDTO(dealer, false);
        return DealerResponseDto.success("Dealer fetched successfully", dto);
    }


    @Override
    public DealerResponseDto getAllDealers() {
        List<Dealer> dealers = dealerRepository.findAll();

        if (dealers.isEmpty()) {
            throw new DealerNotFoundException("No dealers found");
        }

        List<DealerDTO> dealerDTOs = dealers.stream()
                .map(dealer -> DealerMapper.toDTO(dealer, false))
                .toList();

        // use successWithList to populate dataList + totalDealers
        return DealerResponseDto.successWithList("Dealers fetched successfully", dealerDTOs);
    }





    @Override
    public DealerResponseDto getDealersByStatus(DealerStatus status) {
        List<DealerDTO> dealerDTOs = dealerRepository.findAll().stream()
                .filter(d -> d.getStatus() != null && d.getStatus().equals(status))
                .map(dealer -> DealerMapper.toDTO(dealer, false))
                .toList();

        if (dealerDTOs.isEmpty()) {
            throw new DealerNotFoundException("No dealers found with status: " + status);
        }

        return DealerResponseDto.successWithList("Dealers fetched successfully", dealerDTOs);
    }


    @Override
    public DealerResponseDto getDealersByUserId(Long userId) {
        return dealerRepository.findAll().stream()
                .filter(d -> d.getUser() != null && d.getUser().getId().equals(userId.intValue()))
                .findFirst()
                .map(dealer -> DealerResponseDto.success("Dealer fetched successfully", DealerMapper.toDTO(dealer,false)))
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

        DealerDTO dto = DealerMapper.toDTO(savedDealer,false);
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

        if (dealerPage.isEmpty()) {
            throw new DealerNotFoundException("No dealers found for given pagination");
        }

        List<DealerDTO> dealerDTOs = dealerPage.getContent()
                .stream()
                .map(dealer -> DealerMapper.toDTO(dealer, false))
                .toList();

        return DealerResponseDto.successWithList("Dealers fetched successfully", dealerDTOs);
    }



}

