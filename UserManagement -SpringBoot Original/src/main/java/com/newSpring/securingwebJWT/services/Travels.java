package com.newSpring.securingwebJWT.services;

import com.newSpring.securingwebJWT.dto.serialisible.JourneyDto;
import com.newSpring.securingwebJWT.dto.serialisible.UserSer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface Travels {
    //To Load Image into sql
    String sendMultipartFile(MultipartFile multipartFile, String apiUrl, String description, UserSer userSer) throws IOException;

    // To send Image for displaying it in frontend
    ResponseEntity<List<byte[]>> displayMultipartFile(String apiUrl, UserSer userSer);

    String userJrnyTransmitter(UserSer user, JourneyDto journeyDto, String endpoint);

    String userJrnyDeleteTransmitter(UserSer user, JourneyDto journeyDto, String endpoint);

    List<JourneyDto> userTransmitter(UserSer user, String endpoint);


}
