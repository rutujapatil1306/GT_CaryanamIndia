package com.spring.jwt.dealer.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.jwt.dto.DealerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class DealerResponseDto {
    private String message;
    private DealerDTO data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalDealers;
    private String exception;

    public static DealerResponseDto success(String message, DealerDTO data) {
        return new DealerResponseDto(message, data, null, null);
    }

    // Response for only total count (no data list)
    public static DealerResponseDto successWithCount(String message, int totalDealers) {
        return new DealerResponseDto(message, null, totalDealers, null);
    }
    // add one static method
    public static DealerResponseDto error(String message, String exception) {
        return new DealerResponseDto(message, null, 0, exception);
    }
}

