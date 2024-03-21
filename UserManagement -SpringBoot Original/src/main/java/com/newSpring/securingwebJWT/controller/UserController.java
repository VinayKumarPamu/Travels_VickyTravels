package com.newSpring.securingwebJWT.controller;

import com.newSpring.securingwebJWT.dto.AddressDTO;
import com.newSpring.securingwebJWT.dto.ProfileDTO;
import com.newSpring.securingwebJWT.services.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<String> user(){
        return ResponseEntity.ok("HI user");
    }

    @PostMapping("/profile")
    public ResponseEntity<ProfileDTO> setProfile(@RequestBody ProfileDTO profileDTO, HttpServletRequest request){
        String authKey= request.getHeader("Authorization");
        return ResponseEntity.ok(userService.setProfile(profileDTO, authKey));
    }
    @GetMapping("/getProfile")
    public ResponseEntity<ProfileDTO> getProfile(HttpServletRequest request){
        String authKey= request.getHeader("Authorization");
        return ResponseEntity.ok(userService.getProfile(authKey));
    }
    @PostMapping("/address")
    public ResponseEntity<AddressDTO> setAddress(@RequestBody AddressDTO addressDTO, HttpServletRequest request){
        String authKey= request.getHeader("Authorization");
        return ResponseEntity.ok(userService.setAddress(addressDTO,authKey));
    }
    @GetMapping("/getAddress")
    public ResponseEntity<AddressDTO> getAddress(HttpServletRequest request){
        String authKey= request.getHeader("Authorization");
        return ResponseEntity.ok(userService.getAddress(authKey));
    }
    @DeleteMapping("/deleteAccount")
    public ResponseEntity<String> deleteUser(HttpServletRequest request){
        String authKey= request.getHeader("Authorization");
        return ResponseEntity.ok(userService.deleteUser(authKey));
    }

}
