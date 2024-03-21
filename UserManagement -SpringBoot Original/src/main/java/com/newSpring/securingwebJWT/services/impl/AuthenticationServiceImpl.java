package com.newSpring.securingwebJWT.services.impl;

import com.newSpring.securingwebJWT.builder.BuildUser;
import com.newSpring.securingwebJWT.dto.*;
import com.newSpring.securingwebJWT.entities.User;
import com.newSpring.securingwebJWT.repository.UserRepository;
import com.newSpring.securingwebJWT.services.AuthenticationService;
import com.newSpring.securingwebJWT.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BuildUser userBuilder;
    private final TokenService tokenService;
    private final AccountServiceImpl accountService;
    private final JWTServiceImpl jwtService;
    @Override
    public String signup(SignUpRequest signUpRequest){
        if(findByEmail(signUpRequest.getEmail()) == null) {//check it
            UserDTO userDto = new UserDTO();
            userDto.setUsername(signUpRequest.getUsername());
            userDto.setEmail(signUpRequest.getEmail());
            userDto.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            userDto.setRole(signUpRequest.getRole());
            userDto.setRegistrationDateAndTime(accountService.registrationDateAndTime());
            userDto.setLastLogin(accountService.registrationDateAndTime());
            userDto.setAccountStatus(accountService.accountStatus(userDto.getLastLogin()));
            userDto.setLockReleaseDate(LocalDateTime.now());
            User user = userBuilder.buildUser(userDto);
            userRepository.save(user);
            return "user registered";
        }else {
            return null;
        }
    }
    @Override
    public String signin(SignInRequest signIn) {
        System.out.print("Entered into signIn\n");
        var userDto = findByEmail(signIn.getEmail());
        if(userDto == null){
            return "User not found.\nPlease register the user inorder to use the application.";
        }
        LocalDate localDate = userDto.getLockReleaseDate().toLocalDate();
        LocalDate localDate1 = LocalDateTime.now().toLocalDate();
        if(localDate1.isAfter(localDate)){    //Checking Account LockDate and releasing user after Lockdate
            userDto.setFailCount(0);
        }
        if(userDto.getFailCount()>=5){
            return "\nYour Account "+userDto.getEmail()+" has been locked for one day due to multiple login attempts.";
        }

        if (passwordEncoder.matches(signIn.getPassword(),userDto.getPassword()) ){
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
            }catch (BadCredentialsException bcE){
                return "User name or Password Wrong";
            }catch (LockedException lock){
                return "Your Account has been locked";
            }catch (DisabledException | AccountExpiredException disabled){
                return "Your Account is Disabled";
            }catch (NullPointerException Null){
                return "User name or password can not be empty";
            }catch (AuthenticationException ignored){}
            userDto.setAccountStatus(accountService.accountStatus(userDto.getLastLogin()));
            userDto.setAccountExpired(accountService.accountExpired(userDto.getLastLogin()));
            userDto.setCredentialsNonExpired(accountService.credentialsNonExpired(userDto.getRegistrationDateAndTime()));
            userDto.setFailCount(0);
            User user=userBuilder.buildUser(userDto);
            userRepository.save(user);
            var jwtToken = jwtService.generateToken(userDto);
            System.out.print("\nToken for the user "+userDto.getUsername()+" is :\n"+jwtToken+"\n");
            JwtAuthResponse jwtAuthResponse=new JwtAuthResponse();
            jwtAuthResponse.setToken(jwtToken);
            tokenService.generateToken(userDto,jwtAuthResponse);
            System.out.print("User login successful");
            return jwtToken;
        } else {
            userDto.setFailCount(userDto.getFailCount() + 1);
            userDto.setAccountLocked(accountService.accountLocked(userDto.getFailCount()));
            userDto.setLockReleaseDate(accountService.lockReleaseDate(userDto.getFailCount()));
            User user=userBuilder.buildUser(userDto);
            userRepository.save(user);
            return "User name or Password incorrect!\n Please Try Again.";
        }
    }
    @Override
    public UserDTO getUserDetails(String authKey) {
        return findByEmail(jwtService.extractUserName(authKey.substring(7)));
    }

    @Override
    public boolean logout(String authKey) {
        String token=authKey.substring(7);
        String userName=jwtService.extractUserName(token);
        if(userName == null){
            return false;
        }
        UserDTO userDTO=findByEmail(userName);
        userDTO.setLastLogin(accountService.registrationDateAndTime());
        User user=userBuilder.buildUser(userDTO);
        userRepository.save(user);
        return tokenService.logoutUser(token);
    }
    private UserDTO findByEmail(String email){
        Optional<User> findUser = userRepository.findByemail(email);
        if (findUser.isPresent()) {
            return userBuilder.buildUserDto(findUser.get());
        } else {
            return null;
            //throw new IllegalArgumentException("Invalid email or password.");
        }
    }
}
