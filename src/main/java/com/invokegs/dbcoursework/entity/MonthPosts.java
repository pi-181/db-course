package com.invokegs.dbcoursework.entity;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "posts_by_months")
@Subselect("SELECT * FROM posts_by_months")
public class MonthPosts implements Serializable {
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
