package com.newSpring.securingwebJWT.builder;

import com.newSpring.securingwebJWT.dto.serialisible.JourneyDto;
import com.newSpring.securingwebJWT.dto.serialisible.UserSer;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class YourClass {
    private UserSer user;
    private JourneyDto journeyDto;
}
