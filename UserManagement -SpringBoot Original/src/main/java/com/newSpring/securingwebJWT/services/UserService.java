package com.newSpring.securingwebJWT.services;

import com.newSpring.securingwebJWT.dto.AddressDTO;
import com.newSpring.securingwebJWT.dto.ProfileDTO;
import com.newSpring.securingwebJWT.dto.PromoteUserRole;
import com.newSpring.securingwebJWT.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailsService();
    ProfileDTO setProfile(ProfileDTO profileDTO,String authKey);
    ProfileDTO getProfile(String authKey);
    AddressDTO setAddress(AddressDTO addressDTO, String authKey);
    AddressDTO getAddress(String authKey);
    String deleteUser(String authKey);
    String deleteUserByEmail(String email);
    UserDTO findUserByUserName(String email);

    UserDTO getUserDetails(String authKey);
    String setPromotion(PromoteUserRole promoteUserRole);
}

