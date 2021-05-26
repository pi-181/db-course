package com.invokegs.dbcoursework.service.impl;

import com.invokegs.dbcoursework.entity.MonthPosts;
import com.invokegs.dbcoursework.entity.PostCount;
import com.invokegs.dbcoursework.repository.MonthPostsRepository;
import com.invokegs.dbcoursework.repository.PostCountRepository;
import com.invokegs.dbcoursework.service.StatsService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("statsService")
public class StatsServiceImpl implements StatsService {
    private final PostCountRepository postCountRepository;
    private final MonthPostsRepository monthPostsRepository;

    public StatsServiceImpl(PostCountRepository postCountRepository,
                            MonthPostsRepository monthPostsRepository) {
        this.postCountRepository = postCountRepository;
        this.monthPostsRepository = monthPostsRepository;
    }

    @NonNull
    @Override
    public List<PostCount> getAuthorsTop() {
        return postCountRepository.findTop10ByOrderByPostsDesc();
    }

    @NonNull
    @Override
    public List<MonthPosts> getMonthPosts() {
        return monthPostsRepository.getMonths();
    }
}
