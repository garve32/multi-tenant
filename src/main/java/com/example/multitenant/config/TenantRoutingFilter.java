package com.example.multitenant.config;

import com.example.multitenant.context.TenantContext;
import com.example.multitenant.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/* 필터 대신 인터셉터에서 처리 */
@Slf4j
//@Component
@RequiredArgsConstructor
public class TenantRoutingFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tenantId = getTenantIdFromHeader(request);
        log.info("Filter TenantId: {}", tenantId);

        jwtTokenUtil.parseTenantId(tenantId);

        TenantContext.setCurrentTenant(tenantId);
        filterChain.doFilter(request, response);
    }

    private String getTenantIdFromHeader(HttpServletRequest request) {
        String tenantId = request.getHeader("X-TenantID");
        if(tenantId == null || tenantId.isEmpty()) {
            throw new AccessDeniedException("X-TenantID header is missing");
        }
        return tenantId;
    }


}
