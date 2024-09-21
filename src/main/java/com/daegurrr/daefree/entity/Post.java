package com.daegurrr.daefree.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDateTime createAt;

    @Column(length = 2048)
    private String description;

    private String date;

    private String place;

    private String content;

    private String target;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    private int viewCount;
    private int likeCount;
    private int commentCount;

    public Post setAuthor(Account account) {
        this.account = account;
        this.account.getPosts().add(this);
        return this;
    }

    public void addViews() {
        this.viewCount++;
    }

    public void addComments(Comment comment) {
        this.commentCount++;
        this.comments.add(comment);
    }
}
