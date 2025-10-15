package com.spring.jwt.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "pending_booking")
public class PendingBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "price", length = 45)
    private int price;

    @ManyToOne
    @JoinColumn(name = "dealerId")
    private Dealer dealer;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column (name = "asking_price", nullable = false)
    private int askingPrice;

    @ManyToOne
    @JoinColumn(name = "car_id")
    @JsonIgnore
    private Car car;





}