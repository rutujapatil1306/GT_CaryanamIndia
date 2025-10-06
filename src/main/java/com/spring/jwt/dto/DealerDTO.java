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
    @Size(max = 15, message = "Shop name must be less than or equal to 15 characters")
    private String shopName;

    @Pattern(
            regexp = "^[A-Za-z][A-Za-z0-9._%+-]*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "Invalid email format: must start with a letter"
    )
    private String email;

    private long dealerDocumentPhoto;

    @NotNull(message = "Dealer status cannot be null")
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
