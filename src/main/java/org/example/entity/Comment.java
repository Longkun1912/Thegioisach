package org.example.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "comments")
@Getter
@Setter
@RequiredArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String text;

    @Column
    private LocalDateTime created_time;

    @ManyToOne
    @JoinColumn(name = "user_comment", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_comment", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "book_comment", nullable = false)
    private Book book;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<Reply> replies;
}
