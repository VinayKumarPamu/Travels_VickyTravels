package com.newSpring.securingwebJWT.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.newSpring.securingwebJWT.entities.User;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ProfileDTO {
    private Long profileId;
    private User userId;
    private String mobileNumber;
    @JsonProperty("firstName")
    private String First_Name;
    @JsonProperty("lastName")
    private String Last_Name;
    private LocalDateTime Created_At;
    private LocalDateTime Updated_At;
    private String bio;
}
