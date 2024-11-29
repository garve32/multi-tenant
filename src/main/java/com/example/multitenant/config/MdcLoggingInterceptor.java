package com.example.multitenant.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class MdcLoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String handlerMethodName = handlerMethod.getMethod().getName();

        String methodName = handlerMethod.getBeanType().getName();
        String methodInfo = methodName + ":" + handlerMethodName;
        String traceId = UUID.randomUUID().toString();

        log.info("requestURI : {}", requestURI);
        log.info("contextPath : {}", contextPath);
        log.info("servletPath : {}", servletPath);
        log.info("handlerMethodName : {}", handlerMethodName);
        log.info("methodName : {}", methodName);
        log.info("traceId : {}", traceId);
        log.info("methodInfo : {}", methodInfo);

        MDC.put("traceId", traceId);
        MDC.put("methodInfo", methodInfo);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception ex) throws Exception {
        MDC.clear();
    }
}
