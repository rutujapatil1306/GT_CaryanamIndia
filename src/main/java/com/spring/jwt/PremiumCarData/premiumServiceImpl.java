package com.spring.jwt.PremiumCarData;

import com.spring.jwt.entity.Status;
import com.spring.jwt.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class premiumServiceImpl implements PremiumService{
    @Autowired
    private PremiumCarRepository premiumCarRepository;

    @Override
    public PremiumCarDTO save(PremiumCarDTO premiumCarDTO) {

        // Convert DTO -> Entity
        PremiumCar entity = PremiumCarMapper.toEntity(premiumCarDTO);

        // Save entity in DB
        PremiumCar savedEntity = premiumCarRepository.save(entity);

        //Convert Entity -> DTO (with generated ID etc.)
        return PremiumCarMapper.toDto(savedEntity);
    }

    @Override
    public PremiumCarDTO update(PremiumCarDTO premiumCarDTO, Integer id) {
        // 1️⃣ Find the car by ID
        PremiumCar existingCar = premiumCarRepository.findById(id)
                .orElseThrow(() -> new PremiumCarNotFoundException("PremiumCar with ID " + id + " not found"));

        // 2️⃣ Update only the fields provided in DTO
        PremiumCarMapper.updateEntityFromDto(premiumCarDTO, existingCar);

        // 3️⃣ Save updated entity
        PremiumCar updatedCar = premiumCarRepository.save(existingCar);

        // 4️⃣ Convert back to DTO and return
        return PremiumCarMapper.toDto(updatedCar);
    }

    @Override
    public PremiumCarDTO findById(Integer id) {
        PremiumCar car = premiumCarRepository.findById(id)
                .orElseThrow(() -> new PremiumCarNotFoundException("Premium car not found with id: " + id));

        // Convert Entity to DTO
        PremiumCarDTO dto = new PremiumCarDTO();
        //dto.setId(car.getId());
        dto.setBrand(car.getBrand());
        dto.setModel(car.getModel());
        dto.setPrice(car.getPrice());
        dto.setYear(car.getYear());
        dto.setColor(car.getColor());
        dto.setCarStatus(car.getCarStatus());
        dto.setKmDriven(car.getKmDriven());
        dto.setTransmission(car.getTransmission());
        dto.setFuelType(car.getFuelType());
        dto.setDealerId(car.getDealerId());
        // Add all other fields similarly

        return dto;
    }


    @Override
    public PremiumCarDTO deleteById(Integer id) {
        PremiumCar deletecar = premiumCarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Premium Car not found with id: " + id+"not found"));
       premiumCarRepository.delete(deletecar);
        return PremiumCarMapper.toDto(deletecar);
    }

    @Override
    public PremiumCarDTO softDelete(Integer id) {
        PremiumCar car = premiumCarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PremiumCar not found with id: " + id));

        // mark as DEACTIVATE instead of deleting
        car.setCarStatus(Status.DEACTIVATE);
        premiumCarRepository.save(car);

        return PremiumCarMapper.toDto(car);
    }

    @Override
    public PremiumCarDTO hardDelete(Integer id) {
        PremiumCar car = premiumCarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PremiumCar not found with id: " + id));

        // Convert entity to DTO before deleting
        PremiumCarDTO dto = PremiumCarMapper.toDto(car);

        // Permanently delete
        premiumCarRepository.delete(car);

        return dto;
    }

    @Override
    public Page<PremiumCarDTO> getCarsByDealerAndStatus(Integer dealerId, Status status, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<PremiumCar> carsPage = premiumCarRepository.findByDealerIdAndCarStatus(dealerId, status, pageable);

        return carsPage.map(PremiumCarMapper::toDto);
    }


}

