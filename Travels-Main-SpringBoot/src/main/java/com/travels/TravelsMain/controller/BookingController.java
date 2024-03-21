package com.travels.TravelsMain.controller;

import java.util.List;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.travels.TravelsMain.Dto.JourneyDto;
import com.travels.TravelsMain.Dto.User;
import com.travels.TravelsMain.builder.BookingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travels.TravelsMain.service.Booking;

@RequiredArgsConstructor
@RestController
public class BookingController {

	@Autowired
	private ObjectMapper objectMapper;
	private final Booking loginService;

	@GetMapping("/travel-book")
	public String getTravelBook(){
		return "travel-book";

	}
	@PostMapping("/book-travel")
	public ResponseEntity<JourneyDto> bookJourney(@RequestBody BookingRequest bookingRequest){
//		BookingRequest data = objectMapper.readValue(bookingRequest, BookingRequest.class);
		System.out.print("Entered Into Travels");
		User user = bookingRequest.getUser();
		JourneyDto journeyDto = bookingRequest.getJourneyDto();
		System.out.println("Received UserDTO: " + user);
		System.out.println("Received JourneyDTO: " + journeyDto);
		JourneyDto journeyDetails = loginService.journeyDetails(user,journeyDto);
		return ResponseEntity.ok(journeyDetails);
	}
	@PostMapping("/ConfirmTicket")
	public ResponseEntity<String> confirmTicket(@RequestBody BookingRequest bookingRequest) {
		User user = bookingRequest.getUser();
		JourneyDto journeyDto = bookingRequest.getJourneyDto();
		loginService.confirmTicket(user,journeyDto);
			return ResponseEntity.ok("Tickets Confirmed");
	}
	@PostMapping("/history")
	public ResponseEntity<List<JourneyDto>>  getHistory(@RequestBody User user) {
		List<JourneyDto> retriveJourney = loginService.retriveJourney(user.getUserId());
		return ResponseEntity.ok(retriveJourney);
	}

	@PostMapping("/PendingTravel")//Confirmation for editing ticket
	public ResponseEntity<List<JourneyDto>> editTicketDetails(@RequestBody User user) {
		List<JourneyDto> retriveJourney = loginService.findByJourneyDateAfter(user);
		return ResponseEntity.ok(retriveJourney);
	}
	@DeleteMapping("/CancelJourney")//Delete ticket
	public ResponseEntity<String> cancelJourneyDetails(@RequestBody BookingRequest bookingRequest) {
		User user = bookingRequest.getUser();
		JourneyDto journeyDto = bookingRequest.getJourneyDto();
		System.out.print(journeyDto.getJourneyDate());
		loginService.DeleteTicket(journeyDto);
		return ResponseEntity.ok("Ticket Cancelled");
	}


//	@PostMapping("/UpdateTicket")								//Ticket to update		// updated ticket to store
//	public ResponseEntity<String> updateTicket(@RequestBody User user,JourneyDto journeyDto1,JourneyDto journeyDto2) {
//		if(LocalDate.parse(journeyDto1.getJourneyDate()).isAfter(LocalDate.now())) {
//			loginService.DeleteTicket(journeyDto1);
//			JourneyDto journeyDetails = loginService.journeyDetails(user, journeyDto2);
//			loginService.confirmTicket(user, journeyDetails);
//			return ResponseEntity.ok("Ticket Updated");
//		}
//		return ResponseEntity.internalServerError().build();
//	}
}
