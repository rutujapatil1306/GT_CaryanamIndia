package com.spring.jwt.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.jwt.dealer.DealerStatus;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DealerDTO {

    private Integer id; // Add this field
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z0-9._%+-]*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "Invalid email format"
    )
    @Email
    private String email;

    @Pattern(regexp = "^[A-Za-z\\s]{1,15}$", message = "First name must contain only letters")
    private String firstname;

    @Pattern(regexp = "^[A-Za-z\\s]{1,15}$", message = "Last name must contain only letters")
    private String lastName;

    @Pattern(regexp = "^[A-Za-z\\s]{1,15}$", message = "Shop name must contain only letters")
    private String shopName;

    @Pattern(regexp = "^[A-Za-z\\s]{2,30}$", message = "City must contain only letters")
    private String city;

    @Positive(message = "Sales person ID must be positive")
    private Long salesPersonId;

    private String address;
    private String area;

    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid mobile number: must be 10 digits starting with 6-9"
    )
    private String mobileNo;


    private long dealerDocumentPhoto;

    private DealerStatus status;
    // For User entity creation
    @NotBlank(message = "User email cannot be blank")
    private String userEmail;

    @NotBlank(message = "User password cannot be blank")
    private String userPassword;

    @NotBlank(message = "User first name cannot be blank")
    private String userFirstName;
    private String userLastName;
    private String userMobileNumber;
    public void setId(Integer id) {
        this.id = id;
    }


}
