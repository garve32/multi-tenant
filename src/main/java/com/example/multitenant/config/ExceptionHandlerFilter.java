package com.example.multitenant.config;

import com.example.multitenant.model.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        System.out.println("ExceptionHandlerFilter.doFilterInternal");
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            setErrorResponse(response);
        }
    }

    private void setErrorResponse(HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ApiResponse<Object> build = ApiResponse.builder()
            .code(HttpServletResponse.SC_BAD_REQUEST)
            .message("Tenant ID is not valid")
            .data(null)
            .build();
        response.getWriter().println(mapper.writeValueAsString(build));
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @Data
    public static class ErrorResponse {
        private final Integer code;
        private final String message;
    }
}