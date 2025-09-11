package com.spring.jwt.dealer;

import com.spring.jwt.dealer.DealerStatus;
import com.spring.jwt.dto.DealerDTO;
import com.spring.jwt.entity.Dealer;
import com.spring.jwt.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class DealerMapper {

    public static Dealer toEntity(DealerDTO dto) {
        Dealer dealer = new Dealer();
        dealer.setAddress(dto.getAddress());
        dealer.setArea(dto.getArea());
        dealer.setCity(dto.getCity());
        dealer.setFirstname(dto.getFirstname());
        dealer.setLastName(dto.getLastName());
        dealer.setSalesPersonId(dto.getSalesPersonId());
        dealer.setMobileNo(dto.getMobileNo());
        dealer.setShopName(dto.getShopName());
        dealer.setEmail(dto.getEmail());
        dealer.setDealerDocumentPhoto(dto.getDealerDocumentPhoto());
        dealer.setStatus(dto.getStatus());


        // Create User and set it
        User user = new User();
        user.setEmail(dto.getUserEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getUserPassword()));
        user.setFirstName(dto.getUserFirstName());
        user.setLastName(dto.getUserLastName());

        dealer.setUser(user);

        return dealer;
    }

    public static void updateEntityFromDTO(DealerDTO dto, Dealer dealer) {
        if (dto.getAddress() != null) dealer.setAddress(dto.getAddress());
        if (dto.getArea() != null) dealer.setArea(dto.getArea());
        if (dto.getCity() != null) dealer.setCity(dto.getCity());
        if (dto.getFirstname() != null) dealer.setFirstname(dto.getFirstname());
        if (dto.getLastName() != null) dealer.setLastName(dto.getLastName());
        if (dto.getSalesPersonId() != null) dealer.setSalesPersonId(dto.getSalesPersonId());
        if (dto.getMobileNo() != null) dealer.setMobileNo(dto.getMobileNo());
        if (dto.getShopName() != null) dealer.setShopName(dto.getShopName());
        if (dto.getEmail() != null) dealer.setEmail(dto.getEmail());
        if (dto.getDealerDocumentPhoto() != 0) dealer.setDealerDocumentPhoto(dto.getDealerDocumentPhoto());
        if (dto.getStatus() != null) {
            dealer.setStatus(dto.getStatus());
        }
    }
}

