package com.spring.jwt.repository;

import com.spring.jwt.entity.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, Integer> {
    // Add these methods
    boolean existsByEmail(String email);
    boolean existsByMobileNo(Long mobileNo);

    boolean existsBySalesPersonId(Long salesPersonId);
}

