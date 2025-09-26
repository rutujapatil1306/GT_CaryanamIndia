package com.spring.jwt.PremiumCarData;

import lombok.*;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PremiumResponseDTO {
    private String status;   // e.g. "success" / "error"
    private String message;  // e.g. "Car created successfully"
    private Object data;     // can hold PremiumCarDTO or any other object

    public PremiumResponseDTO(String success, String message) {

        this.status = "success";
        this.message = message;
    }
}


