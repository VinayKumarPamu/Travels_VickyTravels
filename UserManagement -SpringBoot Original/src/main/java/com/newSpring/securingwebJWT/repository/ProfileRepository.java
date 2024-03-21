package com.newSpring.securingwebJWT.repository;

import com.newSpring.securingwebJWT.dto.ProfileDTO;
import com.newSpring.securingwebJWT.entities.Profile;
import com.newSpring.securingwebJWT.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile,Long> {
    Optional<Profile> findByUserId(User userId);
    @Modifying
    @Transactional
    @Query("UPDATE Profile p SET " +
            "p.First_Name = :#{#profileDTO.First_Name}, " +
            "p.Last_Name = :#{#profileDTO.Last_Name}, " +
            "p.mobileNumber = :#{#profileDTO.mobileNumber}, " +
            "p.bio = :#{#profileDTO.bio}, " +
            "p.Updated_At=:#{#profileDTO.updated_At} "+
            "WHERE p.userId = :userId")
    void updateProfileByUserId(User userId, ProfileDTO profileDTO);

}
