package com.spring.jwt.PremiumCarData;

import com.spring.jwt.dealer.DealerNotFoundException;
import com.spring.jwt.entity.Dealer;
import com.spring.jwt.entity.Status;
import com.spring.jwt.repository.DealerRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PremiumCarServiceImpl implements PremiumCarService {

    @Autowired
    private PremiumCarRepository premiumCarRepository;

    @Override
    public PremiumCarDTO createPremiumCar(PremiumCarDTO dto) {
        if (dto == null) {
            throw new PremiumCarNotFoundException("PremiumCar data cannot be null");
        }

        try {
            // Convert DTO -> Entity
            PremiumCar entity = PremiumCarMapper.toEntity(dto);

            // Save to DB
            PremiumCar savedEntity = premiumCarRepository.save(entity);

            // Convert Entity -> DTO (return saved details)
            return PremiumCarMapper.toDTO(savedEntity);

        } catch (DataIntegrityViolationException e) {
            throw new PremiumCarNotFoundException("Invalid PremiumCar data: " + e.getMessage());
        } catch (Exception s) {
            throw new ServiceException("Error occurred while saving PremiumCar: " + s.getMessage());
        }
    }

    @Override
    public PremiumCarDTO updatePremiumCar(Integer id, PremiumCarDTO dto) {
        PremiumCar update = premiumCarRepository.findById(id)
                .orElseThrow(() -> new PremiumCarNotFoundException("Car with id " + id + " not found"));

        // Partial update with validation
        if (dto.getBrand() != null) update.setBrand(dto.getBrand());
        if (dto.getVariant() != null) update.setVariant(dto.getVariant());
        if (dto.getPrice() != null && dto.getPrice() > 0) update.setPrice(dto.getPrice());
        if (dto.getYear() != null && dto.getYear() >= 2000) update.setYear(dto.getYear());
        if (dto.getCarstatus() != null) update.setCarstatus(dto.getCarstatus());
        if (dto.getCity() != null) update.setCity(dto.getCity());
        if (dto.getCarInsuranceDate() != null) update.setCarInsuranceDate(dto.getCarInsuranceDate());
        if (dto.getColor() != null) update.setColor(dto.getColor());
        if (dto.getCarType() != null) update.setCarType(dto.getCarType());

        // Boolean flags
        if (dto.getAirbag() != null) update.setAirbag(dto.getAirbag());
        if (dto.getABS() != null) update.setABS(dto.getABS());

        PremiumCar saved = premiumCarRepository.save(update);
        return PremiumCarMapper.toDTO(saved);
    }


    @Override
    public PremiumCarDTO getPremiumCarById(Integer id) {
        PremiumCar car = premiumCarRepository.findById(id)
                .orElseThrow(() -> new PremiumCarNotFoundException("Car with id " + id + " not found"));
        return PremiumCarMapper.toDTO(car);
    }

    @Override
    public void deletePremiumCar(Integer carId, String type) {

        PremiumCar car = premiumCarRepository.findById(carId)
                .orElseThrow(() -> new PremiumCarNotFoundException("Car not found with ID: " + carId));

        if (type.equalsIgnoreCase("soft")) {
            // Soft Delete (mark as inactive)
            car.setCarstatus(Status.ACTIVE); // assuming Status is an enum { ACTIVE, INACTIVE }
            premiumCarRepository.save(car);
        } else if (type.equalsIgnoreCase("hard")) {
            //  Hard Delete (remove from DB)
            premiumCarRepository.delete(car);
        } else {
            throw new IllegalArgumentException("Invalid delete type. Use 'soft' or 'hard'.");
        }
    }




    @Override

    public Page<PremiumCarDTO> getCarsByDealerAndStatus(Long dealerId, Status status, Pageable pageable) {
        Page<PremiumCar> cars;

        if (status != null) {
            cars = premiumCarRepository.findByDealerIdAndCarstatus(dealerId, status, pageable);
        } else {
            cars = premiumCarRepository.findByDealerId(dealerId, pageable);
        }

        if (cars.isEmpty()) {
            throw new PremiumCarNotFoundException("No cars found for dealerId: " + dealerId +
                    (status != null ? " with status: " + status : ""));
        }

        return cars.map(PremiumCarMapper::toDTO);
    }




    public Page<PremiumCarDTO> getCarsByStatus(Status status, int page, int size, String sortBy, String sortDir) {
        // Sorting logic
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        //  Fetch cars by status
        Page<PremiumCar> cars = premiumCarRepository.findByCarstatus(status, pageable);

        if (cars.isEmpty()) {
            throw new PremiumCarNotFoundException("PremiumCar Not found with status: " + status);
        }

        //  Convert entity -> DTO
        return cars.map(PremiumCarMapper::toDTO);
    }

    @Override
    public PremiumCarDTO getCarByMainCarId(String mainCarId) {
        PremiumCar car = premiumCarRepository.findByMainCarId(mainCarId)
                .orElseThrow(() -> new PremiumCarNotFoundException("Car not found with mainCarId: " + mainCarId));
        return PremiumCarMapper.toDTO(car);
    }



}

