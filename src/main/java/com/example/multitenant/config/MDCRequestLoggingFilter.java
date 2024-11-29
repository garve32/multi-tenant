package com.example.multitenant.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MDCRequestLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
        FilterChain filterChain) throws IOException, ServletException {
        System.out.println("MDCRequestLoggingFilter.doFilter");
        try {
            UUID uuid = UUID.randomUUID();
            MDC.put("traceId", uuid.toString());
            // 다음 필터로 제어 전달, 실제 요청이 로직이 실행되는 지점
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            // 실제 요청이 완료되면 MDC 저장소를 초기화
            MDC.clear();
        }
    }

}
