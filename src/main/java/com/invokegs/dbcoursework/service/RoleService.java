package com.invokegs.dbcoursework.service;

import com.invokegs.dbcoursework.entity.Privilege;
import com.invokegs.dbcoursework.entity.Role;
import org.springframework.lang.NonNull;

import java.util.Collection;

public interface RoleService {
    @NonNull
    Role createOrGetRole(String name, String displayName, int priority, Collection<Privilege> privileges);
}
