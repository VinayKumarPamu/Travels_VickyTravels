package com.newSpring.securingwebJWT.builder;

import com.newSpring.securingwebJWT.dto.ProfileDTO;
import com.newSpring.securingwebJWT.entities.Profile;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BuilderProfile {
    private static final ModelMapper modelMapper = new ModelMapper();

    //DTO to Entity conversion
    public static Profile dtoToEntity(ProfileDTO profileDTO) {
        return modelMapper.map(profileDTO, Profile.class);
    }

    //Entity to DTO conversion
    public static ProfileDTO entityToDTO(Profile profile) {
        return modelMapper.map(profile, ProfileDTO.class);
    }
}
