package com.invokegs.dbcoursework.service;

import com.invokegs.dbcoursework.entity.MonthPosts;
import com.invokegs.dbcoursework.entity.PostCount;
import org.springframework.lang.NonNull;

import java.util.List;

public interface StatsService {
    @NonNull
    List<PostCount> getAuthorsTop();
    @NonNull
    List<MonthPosts> getMonthPosts();
}
