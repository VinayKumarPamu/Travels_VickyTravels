package com.newSpring.securingwebJWT.repository;

import com.newSpring.securingwebJWT.dto.AddressDTO;
import com.newSpring.securingwebJWT.entities.Address;
import com.newSpring.securingwebJWT.entities.Profile;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Long> {

    Optional<Address> findByProfileId(Profile profile);

    @Modifying
    @Transactional
    @Query("UPDATE Address a SET " +
            "a.addressLine1 = :#{#addressDTO.addressLine1}, " +
            "a.addressLine2 = :#{#addressDTO.addressLine2}, " +
            "a.city = :#{#addressDTO.city}, " +
            "a.state = :#{#addressDTO.state}, " +
            "a.country = :#{#addressDTO.country}, " +
            "a.isPrimary = :#{#addressDTO.isPrimary}, " +
            "a.Updated_At = :#{#addressDTO.updated_At} "+
            "WHERE a.profileId = :profileId")
    void updateAddressByProfileId(@Param("profileId") Profile profileId, @Param("addressDTO") AddressDTO addressDTO);


}
