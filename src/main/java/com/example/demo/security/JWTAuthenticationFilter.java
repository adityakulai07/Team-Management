package com.example.demo.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.example.demo.entity.UserAdmin;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.example.demo.security.SecurityConstants.EXPIRATION_TIME;
import static com.example.demo.security.SecurityConstants.SECRET;
import static com.example.demo.security.SecurityConstants.HEADER_STRING;
import static com.example.demo.security.SecurityConstants.TOKEN_PREFIX;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // Method when a user tries to log in using credentials
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            UserAdmin creds = new ObjectMapper()
                    .readValue(req.getInputStream(), UserAdmin.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            		);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    // Method to generate a JWT token to user on successful log in
     @Override
     protected void successfulAuthentication(HttpServletRequest req,
                                                HttpServletResponse res,
                                                FilterChain chain,
                                                Authentication auth) throws IOException, ServletException {

            String token = JWT.create()
                    .withSubject(((User) auth.getPrincipal()).getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .sign(HMAC512(SECRET.getBytes()));
            res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        }
}
