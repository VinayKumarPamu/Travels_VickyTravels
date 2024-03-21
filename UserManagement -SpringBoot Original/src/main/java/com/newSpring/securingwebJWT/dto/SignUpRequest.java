package com.newSpring.securingwebJWT.dto;

import com.newSpring.securingwebJWT.entities.Role;
import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String email;
    private String password;
    private Role role;

}
