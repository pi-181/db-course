package com.invokegs.dbcoursework.service;

import com.invokegs.dbcoursework.entity.Privilege;
import org.springframework.lang.NonNull;

public interface PrivilegeService {
    @NonNull
    Privilege createOrGetPrivilege(String name);
}
