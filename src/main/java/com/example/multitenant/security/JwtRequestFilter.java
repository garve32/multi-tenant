package com.example.multitenant.security;

import com.example.multitenant.context.TenantContext;
import com.example.multitenant.context.TenantEnum;
import com.example.multitenant.model.ApiResponse;
import com.example.multitenant.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // fixme : 패턴 매쳐 아님. 패턴 매쳐로 변경 해야 함. /** 이런거 미동작함.
        String[] excludePath = {
                "/auth/login",
                "/api/**"
        };
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 헤더 뒤져서 tenantId, userId 가져오고
//        String token = extractBearerToken(request);
        String token = jwtTokenUtil.extractBearerToken(request);

        log.info("token: {}", token);
        if (StringUtils.hasText(token) && jwtTokenUtil.validateToken(token)) {

            String tenantId = jwtTokenUtil.parseTenantId(token);
            String userId = jwtTokenUtil.parseUserId(token);
            log.info("JwtRequestFilter tenantId = {}, userId = {}", tenantId, userId);
            if(!TenantEnum.hasTenantEnum(tenantId)) {
                ObjectMapper mapper = new ObjectMapper();
                ApiResponse<Object> build = ApiResponse.builder()
                    .code(HttpServletResponse.SC_BAD_REQUEST)
                    .message("Tenant ID is not valid")
                    .data(null)
                    .build();
                response.getWriter().println(mapper.writeValueAsString(build));
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

            // 테넌트 설정 해주고(모듈 앱에서만 하면 될거 같은데, 공통앱에서는 공통DB 사용
            TenantContext.setCurrentTenant(tenantId);

            // 기존 인증을 덮어 쓰지 않도록
            if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
                if(1 == 1 /* token valid 체크 */) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        else {
            log.info("JWT token is Not Valid");
            throw new RuntimeException("JWT token is Not Valid");
        }

        filterChain.doFilter(request, response);
    }

    // fixme : util로 옮기고 삭제
    private String extractBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }


}
