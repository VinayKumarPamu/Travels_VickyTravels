package com.travels.TravelsMain.builder;

import com.travels.TravelsMain.Dto.JourneyDto;
import com.travels.TravelsMain.Dto.User;
import lombok.Data;

@Data
	public class BookingRequest {
		private User user;
		private JourneyDto journeyDto;

		public BookingRequest(User user,JourneyDto journeyDto){
			this.journeyDto=journeyDto;
			this.user=user;
		}
		// Add constructor, getters, and setters
	}