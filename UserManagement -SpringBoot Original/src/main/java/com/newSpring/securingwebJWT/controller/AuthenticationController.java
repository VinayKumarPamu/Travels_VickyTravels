package com.newSpring.securingwebJWT.controller;

import com.newSpring.securingwebJWT.dto.*;
import com.newSpring.securingwebJWT.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequest signUpRequest){
        try{
            //System.out.print("signedIn\n");
            String email = signUpRequest.getEmail();
            if (email.contains("@") && email.contains(".") && email.indexOf(".") > email.indexOf("@") + 2) {
                String signup = authenticationService.signup(signUpRequest);
                if (signup != null) {
                    return ResponseEntity.ok(signup);
                } else {
                    return ResponseEntity.status((HttpStatus.CONFLICT)).body("Email already exists");
                }
            }else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is invalid");
            }
        }catch(Exception exc){
            //exc.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed");
        }

    }

    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody SignInRequest signIn) {
        //System.out.print("signedIn\n");
        String message = authenticationService.signin(signIn);
        if (!message.isEmpty()) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request){
        //System.out.print("Login");
        String authKey= request.getHeader("Authorization");
        if(authenticationService.logout(authKey)){
            return ResponseEntity.ok("Logout successful");
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Logout failed");
        }
    }
}
