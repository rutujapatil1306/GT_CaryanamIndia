package com.spring.jwt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.jwt.dealer.DealerStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DealerDTO {

    private Integer id; // Add this field

    @NotBlank(message = "Address cannot be blank")
    private String address;

    private String area;
    private String city;

    @NotBlank(message = "First name cannot be blank")
    private String firstname;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotNull(message = "Sales person ID cannot be null")
    private Long salesPersonId;

    @NotNull(message = "Mobile number cannot be null")
    @Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits")
    private String mobileNo;

    @NotBlank(message = "Shop name cannot be blank")
    private String shopName;

    @Email(message = "Email should be valid")
    private String email;

    private long dealerDocumentPhoto;

    @NotNull(message = "Dealer status cannot be null")
    private DealerStatus status;

    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userMobileNumber;
}
