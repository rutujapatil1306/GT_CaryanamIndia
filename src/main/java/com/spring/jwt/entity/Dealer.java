package com.spring.jwt.entity;



import com.spring.jwt.dealer.DealerStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
<<<<<<< HEAD
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

=======
import lombok.*;

>>>>>>> f6478de2863350de09dee9e4d298974975739906
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "dealer")
public class Dealer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Dealer_id")
    private Integer id;

    @NotBlank (message = "Address cannot be blank")
    @Column(name = "address")
    private String address;

    @Column(name = "area", nullable = false, length = 45)
    private String area;

    @Column(name = "city", nullable = false, length = 45)
    private String city;

    @Column(name = "firstname", length = 45)
<<<<<<< HEAD
    @NotBlank(message = "Dealer name cannot be empty")
=======
>>>>>>> f6478de2863350de09dee9e4d298974975739906
    private String firstname;

    @Column(name = "last_name", length = 45)
    private String lastName;

    @Column(name = "sales_person_id",unique = true,nullable = false)
    private Long salesPersonId;

    @NotNull(message = "Mobile number is required")
<<<<<<< HEAD
    @Pattern(regexp = "[0-9]{10}", message = "Invalid mobile number format")
    @Column(name = "mobile_no", nullable = false, length = 45)
    private String mobileNo;
=======
//    @Pattern(regexp = "[0-9]{10}", message = "Invalid mobile number format")
//    @Column(name = "mobile_no", nullable = false, length = 45)
    private Long mobileNo;
>>>>>>> f6478de2863350de09dee9e4d298974975739906

    @Column(name = "shop_name", nullable = false, length = 250)
    private String shopName;

    @NotBlank(message = "Email cannot be blank")
    @Column(unique = true)
    private String email;

    private long dealerDocumentPhoto;

//    private Boolean status;
<<<<<<< HEAD
     @Enumerated(EnumType.STRING)
=======
     @Enumerated(EnumType.STRING) // Stores as "ACTIVE", "DEACTIVE", "BLOCKED"
>>>>>>> f6478de2863350de09dee9e4d298974975739906
     @Column(name = "status", nullable = false)
     private DealerStatus status;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "user_user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

<<<<<<< HEAD
    @OneToMany(mappedBy = "dealer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Car> cars = new ArrayList<>();



=======
>>>>>>> f6478de2863350de09dee9e4d298974975739906
//    @OneToMany(mappedBy = "dealerVendor")
//    private Set<Car> cars = new LinkedHashSet<>();




}