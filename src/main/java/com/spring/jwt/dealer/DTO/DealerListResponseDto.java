package com.spring.jwt.dealer.DTO;

import com.spring.jwt.entity.Dealer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DealerListResponseDto {
    private String message;
    private String exception;
    private int totalDealers;
    private List<Dealer> list;

    public static DealerListResponseDto success(String message, List<Dealer> dealers) {
        return new DealerListResponseDto(message, null, dealers.size(), dealers);
    }
    // call cons
    public static DealerListResponseDto error(String message, String exception) {
        return new DealerListResponseDto(message, exception, 0, null);
    }
}
