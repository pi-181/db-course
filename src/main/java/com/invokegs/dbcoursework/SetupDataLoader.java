package com.invokegs.dbcoursework;

import com.invokegs.dbcoursework.entity.Privilege;
import com.invokegs.dbcoursework.service.PrivilegeService;
import com.invokegs.dbcoursework.service.RoleService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final PrivilegeService privileges;
    private final RoleService roles;

    protected boolean loaded = false;

    public SetupDataLoader(PrivilegeService privilege, RoleService roleService) {
        this.privileges = privilege;
        this.roles = roleService;
    }

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (loaded)
            return;

        Privilege readPrivilege = privileges.createOrGetPrivilege("READ_PRIVILEGE");
        Privilege writePrivilege = privileges.createOrGetPrivilege("WRITE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
        roles.createOrGetRole("ROLE_ADMIN", adminPrivileges);
        roles.createOrGetRole("ROLE_USER", Arrays.asList(readPrivilege));

        loaded = true;
    }

}
