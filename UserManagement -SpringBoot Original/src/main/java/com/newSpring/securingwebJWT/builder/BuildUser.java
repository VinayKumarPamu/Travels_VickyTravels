package com.newSpring.securingwebJWT.builder;

import com.newSpring.securingwebJWT.dto.UserDTO;
import com.newSpring.securingwebJWT.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class BuildUser {
    private static final ModelMapper modelMapper = new ModelMapper();

    //Entity to DTO conversion
    public UserDTO buildUserDto(User user){
        return modelMapper.map(user, UserDTO.class);
    }

    //DTO to Entity conversion
    public User buildUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
