package org.example.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "likes")
@Getter
@Setter
@RequiredArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_like", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_like", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "book_like", nullable = false)
    private Book book;
}
