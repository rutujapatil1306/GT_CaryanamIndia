package com.spring.jwt.CarPhoto;

import com.spring.jwt.entity.CarPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
public interface CarPhotoRepository extends JpaRepository<CarPhoto, Integer> {

        //boolean existsByCarIdAndType(Integer carId, DocType type);

        //boolean existsByCarIdAndHashAndIdNot(Integer carId, String hash, Integer id);

        List<CarPhoto> findByCarId(Integer carId);

        Optional<CarPhoto> findByCarIdAndType(Integer carId, DocType type);

            @Transactional
            @Modifying
            @Query("DELETE FROM CarPhoto cp WHERE cp.car.id = :carId")
            void deleteByCarId(@Param("carId") Integer carId);

    //boolean existsByCarIdAndTypeAndIdNot(Integer carId, DocType type, Integer id);

}
