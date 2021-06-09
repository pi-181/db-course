package com.invokegs.dbcoursework.service.impl;

import com.invokegs.dbcoursework.entity.Privilege;
import com.invokegs.dbcoursework.repository.PrivilegeRepository;
import com.invokegs.dbcoursework.service.PrivilegeService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("privilegeService")
public class PrivilegeServiceImpl implements PrivilegeService {
    private final PrivilegeRepository repository;

    public PrivilegeServiceImpl(PrivilegeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @NonNull
    @Override
    public Privilege createOrGetPrivilege(String name) {
        var privilege = repository.findByName(name);

        if (privilege == null) {
            privilege = new Privilege(name);
            repository.save(privilege);
        }

        return privilege;
    }
}
