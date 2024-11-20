package com.example.multitenant.security;

import com.example.multitenant.context.TenantContext;
import com.example.multitenant.model.BspUser;
import com.example.multitenant.model.BspUserDetails;
import com.example.multitenant.repository.UserDetailsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsMapper userDetailsMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // todo : 사용자 정보를 찾아서 가져와야함.
        String currentTenant = TenantContext.getCurrentTenant();
        log.info("UserDetailsServiceImpl userId = {}", username);
        log.info("UserDetailsServiceImpl currentTenant = {}", currentTenant);
        BspUser bspUser = userDetailsMapper.selectUser(username);
        log.info("bspuser = {}", bspUser);
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
