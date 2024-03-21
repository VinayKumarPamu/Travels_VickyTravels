package com.newSpring.securingwebJWT.dto.serialisible;


import lombok.Data;

@Data
public class Image {

    private Long id;

    private byte[] data;

    private String filename;

    private String description;
    //private JourneyDto journeyDto;

}
