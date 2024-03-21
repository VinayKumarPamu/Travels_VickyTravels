package com.newSpring.securingwebJWT.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Setter
@Getter
@Data
@Service
public class JwtAuthResponse {

	private String token;
}
