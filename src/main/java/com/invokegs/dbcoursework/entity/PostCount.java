package com.invokegs.dbcoursework.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_post_count")
public class PostCount implements Serializable {
    @Id
    @Column(name = "user_id")
    Long userId;

    @MapsId
    @OneToOne(mappedBy = "postCount")
    @JoinColumn(name = "user_id")
    private User user;

    private Integer posts;

    public PostCount() {
    }

    public PostCount(User user, Integer posts) {
        this.userId = user.getId();
        this.user = user;
        this.posts = posts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getPosts() {
        return posts;
    }

    public void setPosts(Integer posts) {
        this.posts = posts;
    }
}
