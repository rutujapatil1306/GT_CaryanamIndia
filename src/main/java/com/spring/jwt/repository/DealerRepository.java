package com.spring.jwt.repository;

import com.spring.jwt.entity.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
<<<<<<< HEAD
import org.springframework.stereotype.Service;
=======
>>>>>>> f6478de2863350de09dee9e4d298974975739906

@Repository
public interface DealerRepository extends JpaRepository<Dealer, Integer> {
    // Add these methods
    boolean existsByEmail(String email);
<<<<<<< HEAD
    boolean existsByMobileNo(String mobileNo);
=======
    boolean existsByMobileNo(Long mobileNo);
>>>>>>> f6478de2863350de09dee9e4d298974975739906

    boolean existsBySalesPersonId(Long salesPersonId);
}

