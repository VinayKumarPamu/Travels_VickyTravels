package com.newSpring.securingwebJWT.dto;

import com.newSpring.securingwebJWT.entities.Profile;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddressDTO {
    private Long addressId;
    private Profile profileId;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private LocalDateTime Created_At;
    private LocalDateTime Updated_At;
    private boolean isPrimary;
}
