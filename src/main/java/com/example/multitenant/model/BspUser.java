package com.example.multitenant.model;

import java.util.List;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class BspUser {

    private String userId;
    private String name;
    private String password;
    private String tenantId;
    private List<String> role;

}
