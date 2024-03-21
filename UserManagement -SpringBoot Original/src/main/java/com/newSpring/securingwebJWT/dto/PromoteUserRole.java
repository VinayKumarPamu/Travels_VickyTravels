package com.newSpring.securingwebJWT.dto;

import com.newSpring.securingwebJWT.entities.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PromoteUserRole {
    private String email;
    private Role role;
}
