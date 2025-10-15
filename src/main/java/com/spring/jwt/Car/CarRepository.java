package com.spring.jwt.Car;

import com.spring.jwt.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer>, JpaSpecificationExecutor<Car> {

    boolean existsByMainCarId(String mainCarId);

    List<Car> findByCarStatus(CarStatus carStatus, Pageable pageable);

    long countByCarStatus(CarStatus carStatus);

    List<Car> findByDealerIdAndCarStatus(Integer id, CarStatus carStatus, Pageable pageable);

    long countByDealerIdAndCarStatus(Integer id, CarStatus carStatus);

    Optional<Car> findByMainCarId(String mainCarId);

    List<Car> findByDealer_Id(Integer id);

    List<Car> findByCarStatusIn(List<CarStatus> statuses); // For no pagination
    Page<Car> findByCarStatusIn(List<CarStatus> statuses, Pageable pageable); // For pagination
    long countByCarStatusIn(List<CarStatus> statuses); // For counting

}
