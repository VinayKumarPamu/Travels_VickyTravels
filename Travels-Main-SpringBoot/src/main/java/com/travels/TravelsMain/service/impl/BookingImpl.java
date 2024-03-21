package com.travels.TravelsMain.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.travels.TravelsMain.Dto.JourneyDto;
import com.travels.TravelsMain.Dto.User;
import com.travels.TravelsMain.builder.JourneyRowMapper;
import com.travels.TravelsMain.service.Booking;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.travels.TravelsMain.entity.Journey;
import com.travels.TravelsMain.repository.BookingsRepository;

@AllArgsConstructor
@Service
public class BookingImpl implements Booking {
private final JourneyRowMapper journeyRowMapper;
	private final BookingsRepository loginServiceRepository;

	@Override
	public JourneyDto journeyDetails(User user,JourneyDto jrny) {
		LocalDate jrnyDate = LocalDate.parse(jrny.getJourneyDate());
		float price = 1500;// Cost of Journey Generation //Assumption
        DayOfWeek dayOfWeek = jrnyDate.getDayOfWeek();
		String dayName = dayOfWeek.toString();
		if (dayName.equalsIgnoreCase("Satureday") || dayName.equalsIgnoreCase("Sunday")) {
			price = (float) (price + (200 + 200 * 0.18));
		}
		int passNum = jrny.getPassengerNum();
		float Price=price*passNum;
		System.out.println("Price for Tickets: "+Price);
		jrny.setPrice(Price);
		return jrny;

	}

	@Override
	public void confirmTicket(User user,JourneyDto jrny) {
		jrny.setUserId(user.getUserId());
		Journey journey = journeyRowMapper.dtoToEntity(jrny);
		journey.setJourneyDate(LocalDate.parse(jrny.getJourneyDate()));
		loginServiceRepository.save(journey);
		System.out.println("Journey details Updated");
	}

	@Override
	public List<JourneyDto> retriveJourney(Long Id) {
		List<Journey> byId = loginServiceRepository.findByuserId(Id);//UPDATE IT TO TAKE USER-ID as ID TO SEARCH
		List<JourneyDto> journeyDto = new ArrayList<>();
        for(Journey Journey : byId){
            journeyDto.add(journeyRowMapper.entityToDto(Journey));
		}
		return journeyDto;

	}

	@Override
	public List<JourneyDto> findByJourneyDateAfter(User user) {
		List<Journey> byUserIdAndJourneyDateAfterToday = loginServiceRepository.findByUserIdAndJourneyDateAfterToday(user.getUserId());
		List<JourneyDto> journeyDtos=new ArrayList<>();
		for(Journey journey:byUserIdAndJourneyDateAfterToday){
			journeyDtos.add(journeyRowMapper.entityToDto(journey));
		}
		return journeyDtos ;
	}


	@Override
	public void DeleteTicket(JourneyDto jrny) {
		loginServiceRepository.deleteById(journeyRowMapper.dtoToEntity(jrny).getId());
	}

	@Override
	public JourneyDto findById(Long user) {
		Journey journey1=new Journey();
		List<Journey> byuserId = loginServiceRepository.findByuserId(user);
		for(Journey journey:byuserId){
			journey1=journey;
		}

		return journeyRowMapper.entityToDto(journey1);
	}


}
