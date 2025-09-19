package com.spring.jwt.dealer.DTO;

import com.spring.jwt.entity.Dealer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DealerResponseDto {
    private String message;
    private List<Dealer> data;
    private String exception;

    public static DealerResponseDto success(String message, List<Dealer> data) {
        return new DealerResponseDto(message, data, null);
    }

    public static DealerResponseDto error(String message, String exception) {
        return new DealerResponseDto(message, null, exception);
    }
}

