package com.travels.TravelsMain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Journey {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	String source;
	String destination;
	LocalDate journeyDate;
	int passengerNum;
	float price;
	long userId;

}
