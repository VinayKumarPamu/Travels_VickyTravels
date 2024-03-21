package com.newSpring.securingwebJWT.services;

import java.time.LocalDateTime;


public interface AccountService {

    LocalDateTime registrationDateAndTime();

    boolean accountStatus(LocalDateTime lastLogIn);
}
