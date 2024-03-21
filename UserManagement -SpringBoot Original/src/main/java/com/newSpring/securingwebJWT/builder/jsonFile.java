package com.newSpring.securingwebJWT.builder;

import com.newSpring.securingwebJWT.dto.serialisible.JourneyDto;
import com.newSpring.securingwebJWT.dto.serialisible.UserSer;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Data
@Service
public class jsonFile {
    private UserSer user;
    private MultipartFile multipartFile;
    private String description;
}