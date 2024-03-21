package com.newSpring.securingwebJWT.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;
    @OneToOne
    @JoinColumn(name = "userId")
    private User userId;
    @Column(nullable = false)
    private String mobileNumber;
    @Column(nullable = false)
    private String First_Name;
    @Column(nullable = false)
    private String Last_Name;
    private LocalDateTime Created_At;
    private LocalDateTime Updated_At;
    @Column(columnDefinition = "TEXT")
    private String bio;
}
