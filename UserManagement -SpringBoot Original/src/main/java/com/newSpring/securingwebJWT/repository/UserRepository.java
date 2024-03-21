package com.newSpring.securingwebJWT.repository;

import com.newSpring.securingwebJWT.entities.Role;
import com.newSpring.securingwebJWT.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByemail(String email);

}
