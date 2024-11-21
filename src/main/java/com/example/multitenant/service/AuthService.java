package com.example.multitenant.service;

import com.example.multitenant.model.BspUser;
import com.example.multitenant.repository.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final AuthMapper authMapper;

    public BspUser getUserTenant(String userId) {
        return authMapper.selectUserTenant(userId);
    }
}
