package com.newSpring.securingwebJWT.dto.serialisible;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class JourneyDto {
    long id;
    String source;
    String destination;
    String journeyDate;
    int passengerNum;
    float price;
    long userId;
}


