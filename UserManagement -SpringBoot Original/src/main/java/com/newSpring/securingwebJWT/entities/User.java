package com.newSpring.securingwebJWT.entities;

import com.newSpring.securingwebJWT.dto.AddressDTO;
import com.newSpring.securingwebJWT.dto.ProfileDTO;
import com.newSpring.securingwebJWT.services.UserService;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String email;
    private String password;
    private LocalDateTime registrationDateAndTime;
    private LocalDateTime lastLogin;
    private boolean accountStatus;
    private Role role;
    private boolean isAccountExpired;
    private boolean isAccountLocked;
    private boolean isCredentialsNonExpired;
    private int failCount;
    private LocalDateTime lockReleaseDate;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountLocked;
    }

    @Override
    public boolean isEnabled() {
        return this.accountStatus;
    }

}