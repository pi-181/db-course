package com.invokegs.dbcoursework.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "posts_by_months")
public class MonthPosts {
    @Id
    private String month;
    private Integer count;

    public MonthPosts(String month, Integer count) {
        this.month = month;
        this.count = count;
    }

    public MonthPosts() {
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
