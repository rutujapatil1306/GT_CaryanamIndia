package com.spring.jwt.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.jwt.dealer.DealerStatus;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DealerDTO {

<<<<<<< HEAD
    private Integer id;
=======
    private Integer id; // Add this field

>>>>>>> 70eaad7ab55db1bd984b82e716443094c1ae1fa2
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


    // For User entity creation
    @NotBlank(message = "User email cannot be blank")
    private String userEmail;

    @NotBlank(message = "User password cannot be blank")
    private String userPassword;

    @NotBlank(message = "User first name cannot be blank")
    private String userFirstName;
    private String userLastName;
    private String userMobileNumber;

<<<<<<< HEAD
=======
    public void setId(Integer id) {
    }

>>>>>>> 70eaad7ab55db1bd984b82e716443094c1ae1fa2

}
