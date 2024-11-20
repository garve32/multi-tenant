package com.example.multitenant.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 유효한 인증정보를 가지고 있지 않으면서 로그인이 필요한 리소스에 접근할 때 동작
 */
@Slf4j
@Component
public class BspAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("[BspAuthenticationEntryPoint] :: {}", authException.getMessage());
        log.info("[BspAuthenticationEntryPoint] :: {}", request.getRequestURL());

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        response.setContentType("application/json; charset=UTF-8");

    }
}
