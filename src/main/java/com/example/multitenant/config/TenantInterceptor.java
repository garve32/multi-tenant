package com.example.multitenant.config;

import com.example.multitenant.context.TenantContext;
import com.example.multitenant.context.TenantEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.EnumSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.yaml.snakeyaml.util.EnumUtils;

@Slf4j
@Component
public class TenantInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tenantId = request.getHeader("X-TenantID");

        if (tenantId == null) {
            response.getWriter().println("Tenant ID is required");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }

        //todo - 유효한 테넌트 ID 인지 확인 필요.
        if(!TenantEnum.hasTenantEnum(tenantId)) {
            response.getWriter().println("Tenant ID is not valid");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }

        TenantContext.setCurrentTenant(tenantId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        TenantContext.clear();
    }
}
