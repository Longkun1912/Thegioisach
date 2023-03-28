package org.example.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "dislikes")
@Getter
@Setter
@RequiredArgsConstructor
public class Dislike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_dislike", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_dislike", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "book_dislike", nullable = false)
    private Book book;
}
