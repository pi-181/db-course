package com.invokegs.dbcoursework.repository;

import com.invokegs.dbcoursework.entity.ConfirmToken;
import com.invokegs.dbcoursework.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmTokenRepository extends CrudRepository<ConfirmToken, User> {
    Optional<ConfirmToken> findByToken(String token);
}
