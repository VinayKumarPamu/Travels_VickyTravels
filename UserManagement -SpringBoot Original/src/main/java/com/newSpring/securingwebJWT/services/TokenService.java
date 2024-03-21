package com.newSpring.securingwebJWT.services;

import com.newSpring.securingwebJWT.dto.JwtAuthResponse;
import com.newSpring.securingwebJWT.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface TokenService {
    void generateToken(UserDTO userDTO, JwtAuthResponse jwtAuthResponse);
    boolean logoutUser(String token);

}
