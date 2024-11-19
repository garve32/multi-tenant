package com.example.multitenant.service;

import com.example.multitenant.repository.TenantMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantMapper tenantMapper;

    public int getUserCount() {
        return tenantMapper.countUser();
    }

    public List getUsers() {
        return tenantMapper.getUsers();
    }


}
