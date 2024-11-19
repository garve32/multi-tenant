package com.example.multitenant.config;

import com.example.multitenant.model.BspUserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // todo : 사용자 정보를 찾아서 가져와야함.
        BspUser bspUser = new BspUser();
        return new BspUserDetails(bspUser, getAuthorities(username));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String username) {
        // todo : 권한 쿼리 뒤져서 사용자의 권한 리스트를 가져와야함.
        List<String> userRoles = new ArrayList<String>();
        List<GrantedAuthority> authorities = new ArrayList<>();
        userRoles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));

        return authorities;
    }
}
