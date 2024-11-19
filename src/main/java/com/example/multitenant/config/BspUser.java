package com.example.multitenant.config;

import java.util.List;
import lombok.Getter;

@Getter
public class BspUser {

    private String username;
    private String password;
    private String tenantId;
    private List<String> role;
}
