package com.spring.jwt.repository;
import com.spring.jwt.entity.Dealer;
import com.spring.jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Repository
public interface DealerRepository extends JpaRepository<Dealer, Integer> {
    // Add these methods
    boolean existsByEmail(String email);
    boolean existsByMobileNo(String mobileNo);
    boolean existsBySalesPersonId(Long salesPersonId);
    Optional<Dealer> findByUser(User user);
}

