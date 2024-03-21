package com.newSpring.securingwebJWT.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    @OneToOne
    @JoinColumn(name = "profileId")
    private Profile profileId;
    @Column(nullable = false)
    private String addressLine1;
    private String addressLine2;
    @Column(nullable = false)
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private LocalDateTime Created_At;
    private LocalDateTime Updated_At;
    @Column(nullable = false)
    private boolean isPrimary;

}
