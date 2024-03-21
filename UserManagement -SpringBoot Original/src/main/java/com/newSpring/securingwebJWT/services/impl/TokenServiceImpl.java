package com.newSpring.securingwebJWT.services.impl;

import com.newSpring.securingwebJWT.dto.JwtAuthResponse;
import com.newSpring.securingwebJWT.dto.UserDTO;
import com.newSpring.securingwebJWT.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void generateToken(UserDTO userDTO,JwtAuthResponse jwtAuthResponse){
        redisTemplate.opsForValue().set(jwtAuthResponse.getToken(),generateUserKey(userDTO.getUsername()));
    }

    private String generateUserKey(String userDetails) {
       String KEY = "user";
       return KEY +":"+userDetails;
    }
    @Override
    public boolean logoutUser(String token) {
        try {
            redisTemplate.delete(token);
            return true;
        }catch (Exception fail){
            fail.printStackTrace();
            return false;
        }
    }
}

