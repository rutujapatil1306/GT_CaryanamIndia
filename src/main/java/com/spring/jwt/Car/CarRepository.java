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


    List<Car> findByCarStatusIn(List<Status> statuses);

    Optional<Car> findByMainCarId(String mainCarId);

<<<<<<< HEAD
    List<Car> findByDealer_Id(Integer id);
=======

>>>>>>> d8870504e268337bcfd4886f7f5512c0cd4f7fe2

}
