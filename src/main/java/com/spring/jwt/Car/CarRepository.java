package com.spring.jwt.Car;

import com.spring.jwt.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    boolean existsByMainCarId(String mainCarId);

    List<Car> findByCarStatus(Status carStatus, Pageable pageable);

    long countByCarStatus(Status carStatus);

    List<Car> findByDealerIdAndCarStatus(Integer id, Status carStatus, Pageable pageable);

    long countByDealerIdAndCarStatus(Integer id, Status carStatus);

    Optional<Car> findByMainCarId(String mainCarId);

    List<Car> findByDealer_Id(Integer id);

    List<Car> findByCarStatusIn(List<Status> statuses); // For no pagination
    Page<Car> findByCarStatusIn(List<Status> statuses, Pageable pageable); // For pagination
    long countByCarStatusIn(List<Status> statuses); // For counting

    List<Car> findByCarStatusInAndBrandContainingIgnoreCase(List<Status> statuses, String brand);

    List<Car> findByCarStatusInAndModelContainingIgnoreCase(List<Status> statuses, String model);

    List<Car> findByCarStatusInAndCityContainingIgnoreCase(List<Status> statuses, String city);

    List<Car> findByCarStatusInAndFuelTypeContainingIgnoreCase(List<Status> statuses, String fuelType);

    List<Car> findByCarStatusInAndTransmissionContainingIgnoreCase(List<Status> statuses, String transmission);

    List<Car> findByCarStatusInAndPriceBetween(List<Status> statuses, Integer minPrice, Integer maxPrice);

}
