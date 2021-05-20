package com.invokegs.dbcoursework.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SecurityUserDetails extends org.springframework.security.core.userdetails.User {
    private final User user;

    public SecurityUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, User user) {
        super(username, password, authorities);
        this.user = user;
    }

    public SecurityUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, User user) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
