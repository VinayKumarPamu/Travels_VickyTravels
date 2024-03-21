package com.newSpring.securingwebJWT.services.impl;

import com.newSpring.securingwebJWT.builder.BuildUser;
import com.newSpring.securingwebJWT.builder.BuilderAdr;
import com.newSpring.securingwebJWT.builder.BuilderProfile;
import com.newSpring.securingwebJWT.dto.AddressDTO;
import com.newSpring.securingwebJWT.dto.ProfileDTO;
import com.newSpring.securingwebJWT.dto.PromoteUserRole;
import com.newSpring.securingwebJWT.dto.UserDTO;
import com.newSpring.securingwebJWT.entities.Address;
import com.newSpring.securingwebJWT.entities.Profile;
import com.newSpring.securingwebJWT.entities.User;
import com.newSpring.securingwebJWT.repository.AddressRepository;
import com.newSpring.securingwebJWT.repository.ProfileRepository;
import com.newSpring.securingwebJWT.repository.UserRepository;
import com.newSpring.securingwebJWT.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final BuildUser userBuilder;
    private final JWTServiceImpl jwtService;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService(){
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                // Access userRepository using injection, as it'll be managed by Spring
                com.newSpring.securingwebJWT.entities.User user = userRepository.findByemail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
                String[] roles = {user.getRole().name()};
                // Create UserDetails object (e.g., using Spring Security's User.builder())
                return org.springframework.security.core.userdetails.User.builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .roles(roles)
                        .build();
            }
        };
    }

    @Override
    public ProfileDTO setProfile(ProfileDTO profileDTO,String authKey){
        User user = getUser(authKey);
        profileDTO.setUserId(user);
        Optional<Profile> byUserId = profileRepository.findByUserId(profileDTO.getUserId());
        if (byUserId.isPresent()) {
            profileDTO.setUpdated_At(LocalDateTime.now());
            profileRepository.updateProfileByUserId(profileDTO.getUserId(),profileDTO);
        }else {
            profileDTO.setCreated_At(LocalDateTime.now());
            profileRepository.save(BuilderProfile.dtoToEntity(profileDTO));
        }
        return profileDTO;
    }
    @Override
    public ProfileDTO getProfile(String authKey){
        User user = getUser(authKey);
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setUserId(user);
        Optional<Profile> byUserId = profileRepository.findByUserId(profileDTO.getUserId());
        if(byUserId.isPresent()){
            Profile profile=byUserId.get();
            return BuilderProfile.entityToDTO(profile);
        }else {
            throw new IllegalArgumentException("Profile Details Not Found");
        }
    }

    @Override
    public AddressDTO setAddress(AddressDTO addressDTO, String authKey) {
        Profile profile=getUserProfile(authKey);
        addressDTO.setProfileId(profile);
        Optional<Address> byProfileId = addressRepository.findByProfileId(addressDTO.getProfileId());
        if(addressDTO.isPrimary()){
            addressDTO.setAddressLine2(addressDTO.getAddressLine1());
        }
        if(byProfileId.isPresent()){
            addressDTO.setUpdated_At(LocalDateTime.now());
            addressRepository.updateAddressByProfileId(addressDTO.getProfileId(),addressDTO);
        }else {
            addressDTO.setCreated_At(LocalDateTime.now());
            addressRepository.save(BuilderAdr.dtoToEntity(addressDTO));
        }
        return addressDTO;
    }

    @Override
    public AddressDTO getAddress(String authKey) {
        Profile profile=getUserProfile(authKey);
        AddressDTO addressDTO=new AddressDTO();
        addressDTO.setProfileId(profile);
        Optional<Address> byProfileId = addressRepository.findByProfileId(addressDTO.getProfileId());
        if(byProfileId.isPresent()){
            Address address=byProfileId.get();
            return BuilderAdr.entityToDTO(address);
        }else {
            throw new IllegalArgumentException("Address Details Not Found");
        }
    }

    @Override
    public String deleteUser(String authKey) {
        Address entity = BuilderAdr.dtoToEntity(getAddress(authKey));
        if(entity!=null){
            addressRepository.delete(entity);
        }
        Profile userProfile = getUserProfile(authKey);
        if(userProfile != null) {
            profileRepository.delete(userProfile);
        }
        User user = getUser(authKey);
        userRepository.delete(user);
        return "User account "+user.getEmail()+" Deleted";
    }

    @Override
    public String deleteUserByEmail(String email) {
        Optional<User> byemail = userRepository.findByemail(email);
        User user=new User();
        if(byemail.isPresent()){
            user=byemail.get();
        }
        Optional<Profile> byUserId = profileRepository.findByUserId(user);
        if(byUserId.isPresent()) {  //Checks if profile presents, Deletes the data of profile and address
            Profile profile = byUserId.get();
            //Checks if the address present, deletes data of address
            addressRepository.findByProfileId(profile).ifPresent(addressRepository::delete);
            profileRepository.delete(profile);
        }
        userRepository.delete(user);
        return "User account "+email+" Deleted";
    }

    @Override
    public UserDTO findUserByUserName(String email) {
        return findByEmail(email);
    }

    @Override
    public String setPromotion(PromoteUserRole promoteUserRole) {
        UserDTO byEmail = findByEmail(promoteUserRole.getEmail());
        byEmail.setRole(promoteUserRole.getRole());
        User user = userBuilder.buildUser(byEmail);
        userRepository.save(user);
        return user.getEmail()+" is Promoted to "+promoteUserRole.getRole();
    }

    @Override
    public UserDTO getUserDetails(String authKey){
        User user=getUser(authKey);
        return userBuilder.buildUserDto(user);
    }
    private User getUser(String authKey) {
        String token= authKey.substring(7);
        String userName=jwtService.extractUserName(token);
        UserDTO userDTO=findByEmail(userName);
        return userBuilder.buildUser(userDTO);
    }

    private Profile getUserProfile(String authKey) {
        ProfileDTO profileDTO = new ProfileDTO();
        String token= authKey.substring(7);
        String userName=jwtService.extractUserName(token);
        UserDTO userDTO=findByEmail(userName);
        User user = userBuilder.buildUser(userDTO);
        profileDTO.setUserId(user);
        Optional<Profile> byUserId = profileRepository.findByUserId(profileDTO.getUserId());
        if(byUserId.isPresent()){
            return byUserId.get();
        }else {
            throw new IllegalArgumentException("Profile Details Not Found");
        }
    }
    private UserDTO findByEmail(String email){
        Optional<User> findUser = userRepository.findByemail(email);
        if (findUser.isPresent()) {
            return userBuilder.buildUserDto(findUser.get());
        } else {
            throw new IllegalArgumentException("Invalid email or password.");
        }
    }
}
