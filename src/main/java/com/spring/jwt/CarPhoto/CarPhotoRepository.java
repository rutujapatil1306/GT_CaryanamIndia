package com.spring.jwt.CarPhoto;

import com.spring.jwt.entity.CarPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarPhotoRepository extends JpaRepository<CarPhoto, Integer> {

    boolean existsByCar_IdAndType(int id, DocType type);
}
