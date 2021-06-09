package com.invokegs.dbcoursework.service.impl;

import com.invokegs.dbcoursework.entity.Privilege;
import com.invokegs.dbcoursework.entity.Role;
import com.invokegs.dbcoursework.repository.RoleRepository;
import com.invokegs.dbcoursework.service.RoleService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @NonNull
    @Override
    public Role createOrGetRole(String name, String displayName, int priority, Collection<Privilege> privileges) {
        var role = repository.findByName(name);

        if (role == null) {
            role = new Role(name, displayName, priority);
            role.setPrivileges(privileges);
            repository.save(role);
        }

        return role;
    }
}
