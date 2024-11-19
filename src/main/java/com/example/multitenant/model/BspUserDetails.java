package com.example.multitenant.model;

import com.example.multitenant.config.BspUser;
import java.io.Serializable;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class BspUserDetails extends User implements Serializable {

    private BspUser bspUser;

    public BspUserDetails(BspUser bspUser, Collection<? extends GrantedAuthority> authorities) {
        super(bspUser.getUsername(), null, authorities);
    }
}
