package com.travels.TravelsMain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.travels.TravelsMain.Dto.JourneyDto;
import com.travels.TravelsMain.Dto.User;
import com.travels.TravelsMain.entity.Journey;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingsRepository extends JpaRepository<Journey,Long> {

	@Query("SELECT j FROM Journey j WHERE j.userId = ?1 AND j.journeyDate > CURRENT_DATE")
	List<Journey> findByUserIdAndJourneyDateAfterToday(Long userId);


	Optional<Journey> findById(Long Id);

	@Modifying
	@Transactional
	@Query("UPDATE Journey j SET " +
			"j.source=:#{#journeyDto.source}, " +
			"j.destination=:#{#journeyDto.destination}, " +
			"j.passengerNum=:#{#journeyDto.passengerNum}, " +
			"j.price=:#{#journeyDto.price} " +
			"WHERE j.id = :id")
	void updateJourneyByUserId(@Param("id") User id, @Param("journeyDto") JourneyDto journeyDto);

	List<Journey> findByuserId(Long id);

}
