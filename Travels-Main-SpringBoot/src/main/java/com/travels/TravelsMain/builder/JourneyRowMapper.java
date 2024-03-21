package com.travels.TravelsMain.builder;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.travels.TravelsMain.Dto.JourneyDto;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.RowMapper;

import com.travels.TravelsMain.entity.Journey;
import org.springframework.stereotype.Component;

@Component
public class JourneyRowMapper{
    private  final ModelMapper modelMapper = new ModelMapper();
    public  Journey dtoToEntity(JourneyDto journeyDto){
        return modelMapper.map(journeyDto,Journey.class);
    }
    public  JourneyDto entityToDto(Journey journey){
        return modelMapper.map(journey,JourneyDto.class);
    }
}