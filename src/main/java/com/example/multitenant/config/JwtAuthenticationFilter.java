package com.example.multitenant.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {

        System.out.println("JwtAuthenticationFilter.attemptAuthentication");

        ObjectMapper objectMapper = new ObjectMapper();
        //todo 1. request 분석 - ID / PW


        //todo 2. 로그인 검증


        //todo 3. Authentication 반환
        return super.attemptAuthentication(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult)
        throws IOException, ServletException {

        System.out.println("JwtAuthenticationFilter.successfulAuthentication");

        //todo 4. JWT 발급
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
