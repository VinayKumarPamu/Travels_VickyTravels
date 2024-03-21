package com.newSpring.securingwebJWT.config;

import com.newSpring.securingwebJWT.services.JWTservice;
import com.newSpring.securingwebJWT.services.impl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTservice jwTservice;
    private final UserServiceImpl userService;
    //Filter Chain
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader=request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        //Checks Authorization header empty or not
        //If empty -- calls doFilter

        if(!StringUtils.hasText(authHeader)||!org.apache.commons.lang3.StringUtils.startsWith(authHeader,"Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        jwt=authHeader.substring(7);
        userEmail=jwTservice.extractUserName(jwt);
        System.out.print(userEmail);

        //Checks User's email empty or not
        //If empty -- calls doFilter

        if (StringUtils.hasText(userEmail)&& SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=userService.userDetailsService().loadUserByUsername(userEmail);

            // Checks the jwt token and validates with the retrieved user details by "userDetailsService().loadUserByUsername(userEmail)"
            //If found expired-- calls doFilter

            if(jwTservice.isTokenValid(jwt,userDetails)){
                SecurityContext securityContext=SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(
                    userDetails,null,userDetails.getAuthorities()
                );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
                System.out.print(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            }
        }
        filterChain.doFilter(request,response);
    }
}
