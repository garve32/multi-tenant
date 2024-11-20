package com.example.multitenant.model;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class BspUserDetails extends User implements Serializable {

    private BspUser bspUser;

    public BspUserDetails(BspUser bspUser, Collection<? extends GrantedAuthority> authorities) {
        super(bspUser.getUserId(), bspUser.getPassword(), authorities);
        this.bspUser = bspUser;
    }
}
