package com.newSpring.securingwebJWT.services;

import com.newSpring.securingwebJWT.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTservice {
    String extractUserName(String token);
    String generateToken(UserDTO userDetails);
    boolean isTokenValid(String token,UserDetails userDetails);
}
