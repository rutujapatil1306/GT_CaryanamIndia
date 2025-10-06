package com.spring.jwt.dto;
import com.spring.jwt.entity.Dealer;
import com.spring.jwt.entity.Role;
import com.spring.jwt.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;
import java.util.stream.Collectors;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotBlank(message = "Email cannot be blank")
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z0-9._%+-]*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "Invalid email format: must start with a letter"
    )
    @Schema(
            description = "Email of User", example = "example@example.com"
    )
    private String email;

    @Schema(
            description = "userId of User", example = "10011"
    )
    private String userId;

    @NotBlank(message = "password is required")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long and include one uppercase letter, one lowercase letter, one number, and one special character"
    )
    @Schema(
            description = "Password to create an account", example = "Pass@1234"
    )
    private String password;

    @Schema(
            description = "Address of the customer", example = "A/P Pune Main Street Block no 8"
    )
    private String address;

    @NotBlank(message = "firstName is required")
    @Pattern(regexp = "^[A-Za-z\\s]{1,15}$", message = "First name must contain only letters and be less than 15 characters")
    @Schema(
            description = "First Name of the customer", example = "John"
    )
    private String firstName;

    @NotBlank(message = "lastName is required")
    @Pattern(regexp = "^[A-Za-z\\s]{1,15}$", message = "Last name must contain only letters and be less than 15 characters")
    @Schema(
            description = "Last Name of the customer", example = "Doe"
    )
    private String lastName;

    @Min(value = 7000000000L, message = "Mobile Number Starts with 7,8 or 9 only")
    @Max(value = 9999999999L, message = "Mobile Number Starts with 7,8 or 9 only")
    @Schema(
            description = "Mobile Number of the customer", example = "9822222212"
    )
    private Long mobileNumber;

    @Schema(
            description = "Roles of the User", example = "[\"USER\", \"ADMIN\"]"
    )
    private Set<String> roles;

    private String name;
    private String dateOfBirth;
    private String studentcol;
    private String studentcol1;
    private String studentClass;
    private String role; // Single role field for backward compatibility


    // added for dealer
    private Dealer id;

    @Pattern(regexp = "^[A-Za-z\\s]{1,15}$", message = "Shop name must contain only letters and be less than 15 characters")
    private String shopName;
    @NotBlank(message = "area is required")
    private String area;
    @Pattern(regexp = "^[A-Za-z\\s]{2,30}$", message = "City must contain only letters")
    private String city;

    @Positive(message = "Sales person ID must be positive")
    private Long salesPersonId;

    private long dealerDocumentPhoto;


    public UserDTO(User user) {
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.mobileNumber = user.getMobileNumber();
        this.userId = user.getId().toString();
        // added



        if (user.getRoles() != null) {
            this.roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        }
    }

    // Static method to create DTO from User entity
    public static UserDTO fromEntity(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setEmail(user.getEmail());
        dto.setMobileNumber(user.getMobileNumber());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setAddress(user.getAddress());
        dto.setUserId(user.getId().toString());
        
        if (user.getRoles() != null) {
            dto.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet()));
        }
        
        return dto;
    }
}
