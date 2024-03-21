package com.newSpring.securingwebJWT.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newSpring.securingwebJWT.entities.Role;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
public class UserDTO {

    private Long userId;
    private String username;
    private String email;
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registrationDateAndTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLogin;
    private boolean accountStatus;
    private Role role;
    private boolean isAccountExpired;
    private boolean isAccountLocked;
    private boolean isCredentialsNonExpired;
    private int failCount;
    private LocalDateTime lockReleaseDate;

}