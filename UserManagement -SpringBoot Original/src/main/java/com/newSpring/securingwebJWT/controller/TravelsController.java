package com.newSpring.securingwebJWT.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newSpring.securingwebJWT.builder.YourClass;
import com.newSpring.securingwebJWT.dto.serialisible.JourneyDto;
import com.newSpring.securingwebJWT.dto.UserDTO;
import com.newSpring.securingwebJWT.dto.serialisible.UserSer;
import com.newSpring.securingwebJWT.services.impl.TravelsImpl;
import com.newSpring.securingwebJWT.services.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TravelsController {

    private final UserServiceImpl userService;
    private final TravelsImpl travels;

    @PostMapping("/bookTravel")
    public ResponseEntity<String> TravelBooking(@RequestBody JourneyDto journeyDTO, HttpServletRequest request) {
        String authKey= request.getHeader("Authorization");
        UserDTO user = userService.getUserDetails(authKey);
        UserSer userSer=new UserSer();
        userSer.setUserId(user.getUserId());
        String endpoint = "/book-travel";
        String response = travels.userJrnyTransmitter(userSer,journeyDTO, endpoint);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/ConfirmTicket")
    public ResponseEntity<String> ConfirmTicket(@RequestBody JourneyDto journeyDTO, HttpServletRequest request) {
        String authKey= request.getHeader("Authorization");
        UserDTO user = userService.getUserDetails(authKey);
        UserSer userSer=new UserSer();
        userSer.setUserId(user.getUserId());
        String endpoint = "/ConfirmTicket";
        String response = travels.userJrnyTransmitter(userSer,journeyDTO, endpoint);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/history")
    public ResponseEntity<List<JourneyDto>> history(HttpServletRequest request){
        String authKey= request.getHeader("Authorization");
        UserDTO user = userService.getUserDetails(authKey);
        UserSer userSer=new UserSer();
        userSer.setUserId(user.getUserId());
        String endpoint="/history";
        List<JourneyDto> response = travels.userTransmitter(userSer, endpoint);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/PendingTravel")
    public ResponseEntity<List<JourneyDto>> pending(HttpServletRequest request){
        String authKey= request.getHeader("Authorization");
        UserDTO user = userService.getUserDetails(authKey);
        UserSer userSer=new UserSer();
        userSer.setUserId(user.getUserId());
        String endpoint="/PendingTravel";
        List<JourneyDto> response = travels.userTransmitter(userSer, endpoint);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/CancelJourney")//NEED To SEND FULL Details With Id
    public ResponseEntity<String> calcelJourney(HttpServletRequest request,@RequestBody JourneyDto journeyDto){
        String authKey= request.getHeader("Authorization");
        UserDTO user = userService.getUserDetails(authKey);
        UserSer userSer=new UserSer();
        userSer.setUserId(user.getUserId());
        String endpoint = "/CancelJourney";
        String response = travels.userJrnyDeleteTransmitter(userSer,journeyDto, endpoint);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file,
                            @RequestParam("description") String description, HttpServletRequest request) throws IOException {
        String authKey= request.getHeader("Authorization");
        UserDTO user = userService.getUserDetails(authKey);
        UserSer userSer=new UserSer();
        userSer.setUserId(user.getUserId());
        String endpoint="/images/upload";
        return travels.sendMultipartFile(file,endpoint,description,userSer);
    }
    @GetMapping("/display")
    public ResponseEntity<List<byte[]>> displayImage(HttpServletRequest request) throws IOException {
        String authKey= request.getHeader("Authorization");
        UserDTO user = userService.getUserDetails(authKey);
        UserSer userSer=new UserSer();
        userSer.setUserId(user.getUserId());
        String endpoint="/images/Display";
        return travels.displayMultipartFile(endpoint,userSer);
    }
}
