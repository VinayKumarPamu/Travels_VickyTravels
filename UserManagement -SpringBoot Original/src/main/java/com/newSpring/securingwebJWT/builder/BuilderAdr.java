package com.newSpring.securingwebJWT.builder;

import com.newSpring.securingwebJWT.dto.AddressDTO;
import com.newSpring.securingwebJWT.entities.Address;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BuilderAdr {
    private static final ModelMapper modelMapper = new ModelMapper();

    //DTO to Entity conversion
    public static Address dtoToEntity(AddressDTO addressDTO){
        return modelMapper.map(addressDTO, Address.class);
    }

    //Entity to DTO conversion
    public static AddressDTO entityToDTO(Address address){
        return modelMapper.map(address, AddressDTO.class);
    }
}
