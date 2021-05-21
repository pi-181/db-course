package com.invokegs.dbcoursework.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_post_count", indexes = {

})
public class PostCount implements Serializable {
    @Id
    @Column(name = "user_id")
    Long userId;

    @MapsId
    @OneToOne(mappedBy = "postCount")
    @JoinColumn(name = "user_id")
    private User user;

    private int posts;

    public PostCount() {
    }

    public PostCount(User user, int posts) {
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

    public int getPosts() {
        return posts;
    }

    public void setPosts(int posts) {
        this.posts = posts;
    }
}
