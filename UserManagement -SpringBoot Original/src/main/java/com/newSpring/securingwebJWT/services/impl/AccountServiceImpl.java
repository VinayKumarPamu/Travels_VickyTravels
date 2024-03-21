package com.newSpring.securingwebJWT.services.impl;

import com.newSpring.securingwebJWT.services.AccountService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountServiceImpl implements AccountService {

    @Override
    public LocalDateTime registrationDateAndTime(){
        return LocalDateTime.now();
    }

    @Override
    public boolean accountStatus(LocalDateTime lastLogIn){
        return !lastLogIn.plusDays(60).equals(LocalDateTime.now());

    }

    public boolean credentialsNonExpired(LocalDateTime registrationDate){
        return !registrationDate.plusDays(90).equals(LocalDateTime.now());
    }

    public boolean accountExpired(LocalDateTime lastLogIn){
        return lastLogIn.plusDays(180).equals(LocalDateTime.now());
    }

    public boolean accountLocked(int failCount){
        return failCount == 5;
    }
    public LocalDateTime lockReleaseDate(int failCount) {
        if (failCount == 5) {
            return LocalDateTime.now().plusDays(1);
        } else {
            return LocalDateTime.now();
        }
    }
}
