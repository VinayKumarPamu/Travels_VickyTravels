package com.travels.TravelsMain.Dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class JourneyDto {
    long id;
    String source;
    String destination;
    String journeyDate;
    int passengerNum;
    float price;
    long userId;



}


