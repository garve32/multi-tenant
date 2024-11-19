package com.example.multitenant.controller;

import com.example.multitenant.context.TenantContext;
import com.example.multitenant.service.TenantService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
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

    @GetMapping("/users")
    public ResponseEntity<List> getUsers() {
        String currentTenant = TenantContext.getCurrentTenant();
        log.info("Current Tenant: " + currentTenant);
        List user = tenantService.getUsers();
        log.info("User: " + user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
