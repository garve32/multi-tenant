package com.example.multitenant.config;

import com.example.multitenant.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
public class MDCRequestLoggingFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("MDCRequestLoggingFilter.doFilter");
        try {
            UUID uuid = UUID.randomUUID();
            String requestURI = request.getRequestURI();
            MDC.put("traceId", uuid.toString());
            String tenantIdFromHeader = jwtTokenUtil.extractBearerToken(request);
            System.out.println("tenantIdFromHeader = " + tenantIdFromHeader);
            MDC.put("tenantId", tenantIdFromHeader);
            // 다음 필터로 제어 전달, 실제 요청이 로직이 실행되는 지점
            filterChain.doFilter(request, response);
        } finally {
            // 실제 요청이 완료되면 MDC 저장소를 초기화
            MDC.clear();
        }
    }

//    @Override
//    public int getOrder() {
//        return Ordered.HIGHEST_PRECEDENCE;
//    }
}
