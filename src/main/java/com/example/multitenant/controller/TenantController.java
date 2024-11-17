package com.example.multitenant.controller;

import com.example.multitenant.context.TenantContext;
import com.example.multitenant.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TenantController {

    private final TenantService tenantService;

    @GetMapping("/count")
    public String getCountUser() {
        String currentTenant = TenantContext.getCurrentTenant();
        int count = tenantService.getUserCount();
        return "Tenant: " + currentTenant + " count: " + count;
    }
}
