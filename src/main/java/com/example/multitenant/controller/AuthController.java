package com.example.multitenant.controller;

import com.example.multitenant.model.LoginRequest;
import com.example.multitenant.model.LoginResponse;
import com.example.multitenant.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/getTempToken")
    public String authenticateAndGetToken() {
        log.info("authenticateAndGetToken");
        return jwtTokenUtil.createAccessTokenTemp();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        log.info("ID/PW login = {}", loginRequest);
        // todo : 사용자 조회해서 테넌트ID를 가져와야함. 임시 비전 세팅
        String tenantId = "517";
        String token = jwtTokenUtil.createAccessToken(loginRequest.getUserId(), tenantId);

        LoginResponse res = new LoginResponse();
        res.setToken(token);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/sloLogin")
    public String sloLogin() {
        log.info("sloLogin");
        return jwtTokenUtil.createAccessTokenTemp();
    }
}
