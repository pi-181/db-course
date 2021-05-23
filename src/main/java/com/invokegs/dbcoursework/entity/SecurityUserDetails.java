package com.invokegs.dbcoursework.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Optional;

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

    public boolean hasAuthority(String authority) {
        return getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(authority));
    }

    public static Optional<SecurityUserDetails> getSessionUserDetails() {
        Object details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (details instanceof SecurityUserDetails y) {
            return Optional.of(y);
        }

        return Optional.empty();
    }
}
