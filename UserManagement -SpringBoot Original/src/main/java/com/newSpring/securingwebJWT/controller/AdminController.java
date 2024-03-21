package com.newSpring.securingwebJWT.controller;

import com.newSpring.securingwebJWT.dto.AddressDTO;
import com.newSpring.securingwebJWT.dto.ProfileDTO;
import com.newSpring.securingwebJWT.dto.PromoteUserRole;
import com.newSpring.securingwebJWT.dto.UserDTO;
import com.newSpring.securingwebJWT.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> admin(){
        return ResponseEntity.ok("ADMIN Role Here");
    }
    @GetMapping("/findUser")                //Getting User Details by their UserName
    public ResponseEntity<UserDTO> getUser(@RequestBody String email) {
        UserDTO userDTO=userService.findUserByUserName(email);
        if(userDTO!=null){
            return ResponseEntity.ok(userDTO);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PostMapping("/profile")                //Set Profile
    public ResponseEntity<ProfileDTO> setProfile(@RequestBody ProfileDTO profileDTO, HttpServletRequest request){
        String authKey= request.getHeader("Authorization");
        return ResponseEntity.ok(userService.setProfile(profileDTO, authKey));
    }
    @GetMapping("/getProfile")              //Get Profile
    public ResponseEntity<ProfileDTO> getProfile(HttpServletRequest request){
        String authKey= request.getHeader("Authorization");
        return ResponseEntity.ok(userService.getProfile(authKey));
    }
    @PostMapping("/address")                //Set Address
    public ResponseEntity<AddressDTO> setAddress(@RequestBody AddressDTO addressDTO, HttpServletRequest request){
        String authKey= request.getHeader("Authorization");
        return ResponseEntity.ok(userService.setAddress(addressDTO,authKey));
    }
    @GetMapping("/getAddress")              //Get Address
    public ResponseEntity<AddressDTO> getAddress(HttpServletRequest request){
        String authKey= request.getHeader("Authorization");
        return ResponseEntity.ok(userService.getAddress(authKey));
    }
    @DeleteMapping("/deleteAccount")        //Delete Account
    public ResponseEntity<String> deleteUser(HttpServletRequest request){
        String authKey= request.getHeader("Authorization");
        return ResponseEntity.ok(userService.deleteUser(authKey));
    }
    @DeleteMapping("/deleteUserByEmail")    //Deleting User by UserName(Only Done by Admin)
    public ResponseEntity<String> deleteUserByEmail(@RequestBody String email){
        return ResponseEntity.ok(userService.deleteUserByEmail(email));
    }
    @PostMapping("/promote")                //Promoting User by UserName(Only by Admin)
    public ResponseEntity<String> setPromotion(@RequestBody PromoteUserRole promoteUserRole){
        return ResponseEntity.ok(userService.setPromotion(promoteUserRole));
    }
}
