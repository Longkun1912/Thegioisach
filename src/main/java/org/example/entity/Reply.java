package org.example.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "replies")
@Getter
@Setter
@RequiredArgsConstructor
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String text;

    @Column
    private LocalDateTime created_time;

    @ManyToOne
    @JoinColumn(name = "user_reply", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_reply", nullable = false)
    private Comment comment;
}
