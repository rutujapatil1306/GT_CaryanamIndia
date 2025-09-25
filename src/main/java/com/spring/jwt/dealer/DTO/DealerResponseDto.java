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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DealerDTO data;  // for single dealer

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<DealerDTO> dataList; // fetch multiple dealers

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalDealers;

    private String exception;

    public static DealerResponseDto success(String message, DealerDTO data) {
        return new DealerResponseDto(message, data, null, null, null);
    }

    public static DealerResponseDto successWithList(String message, List<DealerDTO> dataList) {
        return new DealerResponseDto(message, null, dataList, dataList.size(), null);
    }

    public static DealerResponseDto error(String message, String exception) {
        return new DealerResponseDto(message, null, null, null, exception);
    }
}
