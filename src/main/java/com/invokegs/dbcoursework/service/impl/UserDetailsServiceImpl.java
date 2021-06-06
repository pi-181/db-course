package com.invokegs.dbcoursework.service.impl;

import com.invokegs.dbcoursework.entity.Role;
import com.invokegs.dbcoursework.security.SecurityUserDetails;
import com.invokegs.dbcoursework.entity.User;
import com.invokegs.dbcoursework.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findUserByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException(username);

        return new SecurityUserDetails(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                getAuthorities(user.getRoles()),
                user
        );
    }

    @Bean
    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            role.getPrivileges().stream()
                    .map(p -> new SimpleGrantedAuthority(p.getName()))
                    .forEach(authorities::add);
        }

        return authorities;
    }

}
