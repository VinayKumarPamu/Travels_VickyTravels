package com.newSpring.securingwebJWT.services.impl;

import com.newSpring.securingwebJWT.dto.UserDTO;
import com.newSpring.securingwebJWT.services.JWTservice;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTservice {

    public String generateToken(UserDTO userDetails){
        Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 24 * 2);
        System.out.println("Token Expiration Date: " + expirationDate);

// Set expiration date in the JWT builder
        return Jwts.builder()
                .setSubject(userDetails.getEmail())
                .claim("role",userDetails.getRole())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractUserName(String token){
        return extractClaim(token,Claims::getSubject);
    }

    private <T>T extractClaim(String token, Function<Claims,T> claimsResolvers){
        final Claims claims=extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignKey() {
        byte[] key= Decoders.BASE64.decode("VinayKumarPamu12345678909876543217989982511");
        return Keys.hmacShaKeyFor(key);
    }
    public boolean isTokenValid(String token,UserDetails userDetails){
        return (extractUserName(token).equals(userDetails.getUsername())&&!isTokenExpired(token));
    }
    private boolean isTokenExpired(String token){
        return extractClaim(token,Claims::getExpiration).before(new Date());
    }
}

