package org.example.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "posts")
@Getter
@Setter
@RequiredArgsConstructor
public class Post {
    @Id
    private UUID id;

    @Column
    private String title;

    @Column
    private byte[] content_image;

    @Column
    private String content_text;

    @Column
    private LocalDateTime created_time;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Rate> rates;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    // A post created by only one user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_post", referencedColumnName = "id")
    private User creator;

    // A post can be shared to many users
    @ManyToMany(mappedBy = "sharedPosts", cascade = CascadeType.ALL)
    private List<User> sharedBy = new ArrayList<>();
}
