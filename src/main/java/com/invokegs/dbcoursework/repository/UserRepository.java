package com.invokegs.dbcoursework.repository;

import com.invokegs.dbcoursework.entity.User;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Nullable
    User findUserByUsername(String username);

    @Nullable
    User findUserByUsernameOrEmail(String username, String email);

    @Procedure(procedureName = "use_token", outputParameterName = "activated")
    boolean activateUser(@Param("token") String token);
}
