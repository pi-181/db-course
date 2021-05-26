package com.invokegs.dbcoursework.repository;

import com.invokegs.dbcoursework.dto.MonthPostsDto;
import com.invokegs.dbcoursework.entity.VoidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthPostsRepository extends JpaRepository<VoidEntity, VoidEntity> {
    @Query(nativeQuery = true, value = "SELECT * FROM posts_by_months")
    List<MonthPostsDto> getMonths();
}
