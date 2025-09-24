package com.spring.jwt.dealer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.jwt.dealer.DealerStatus;
import com.spring.jwt.dto.DealerDTO;
import com.spring.jwt.entity.Dealer;
import com.spring.jwt.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class DealerMapper {


    public static DealerDTO toDTO(Dealer dealer,boolean includeUser) {
        DealerDTO dto = new DealerDTO();
        dto.setId(dealer.getId());
        dto.setAddress(dealer.getAddress());
        dto.setArea(dealer.getArea());
        dto.setCity(dealer.getCity());
        dto.setFirstname(dealer.getFirstname());
        dto.setLastName(dealer.getLastName());
        dto.setSalesPersonId(dealer.getSalesPersonId());
        dto.setMobileNo(dealer.getMobileNo());
        dto.setShopName(dealer.getShopName());
        dto.setEmail(dealer.getEmail());
        dto.setDealerDocumentPhoto(dealer.getDealerDocumentPhoto());
        dto.setStatus(dealer.getStatus());

        if (includeUser &&dealer.getUser() != null) {
            dto.setUserFirstName(dealer.getUser().getFirstName());
            dto.setUserLastName(dealer.getUser().getLastName());
            dto.setUserEmail(dealer.getUser().getEmail());
            dto.setUserMobileNumber(String.valueOf(dealer.getUser().getMobileNumber()));
        }
        return dto;
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

