package com.invokegs.dbcoursework.entity;

import javax.persistence.*;

@Entity
@Table(name = "post", indexes = {
        @Index(name = "title_index", columnList = "title"),
        @Index(name = "author_index", columnList = "author_id")
})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "varchar(50)")
    private String title;

    @Column(columnDefinition = "text")
    private String content;

    @ManyToOne(optional = false)
    private User author;

    public Post() {
    }

    public Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Post(Long id, String title, String content, User author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
