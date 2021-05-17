package com.invokegs.dbcoursework.repository;

import com.invokegs.dbcoursework.entity.Privilege;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends CrudRepository<Privilege, Long> {
    @Nullable
    Privilege findByName(String name);
}
