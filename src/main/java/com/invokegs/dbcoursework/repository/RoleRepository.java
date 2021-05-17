package com.invokegs.dbcoursework.repository;

import com.invokegs.dbcoursework.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    @Nullable
    Role findByName(String name);
}
