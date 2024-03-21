package com.travels.TravelsMain.service;

import java.util.List;

import com.travels.TravelsMain.Dto.JourneyDto;
import com.travels.TravelsMain.Dto.User;
import com.travels.TravelsMain.entity.Journey;

public interface Booking {

	JourneyDto journeyDetails(User user,JourneyDto journeyDto);
	void confirmTicket(User user,JourneyDto journeyDto);
	List<JourneyDto> retriveJourney(Long Id);
	List<JourneyDto> findByJourneyDateAfter(User user);
	void DeleteTicket(JourneyDto jrny);

	JourneyDto findById(Long user);
}
