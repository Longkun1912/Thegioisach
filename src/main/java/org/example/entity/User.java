package org.example.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    private UUID id;

    @Column
    private String username;

    @Column
    private String image;

    @Column
    private String email;

    @Column
    private String phone_number;

    @Column
    private String status;

    @Column
    private LocalDateTime last_updated;

    @Column
    private String password;

    @ManyToOne
    @JoinColumn(name = "user_role", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Rate> rates;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Favorite> favorites;

    // One user can have many chats with different users
    // One user can have only one chat to one specific user
    @OneToMany(mappedBy = "user1", cascade = CascadeType.ALL)
    private List<Chat> chat_user1;

    @OneToMany(mappedBy = "user2", cascade = CascadeType.ALL)
    private List<Chat> chats_user2;

    // An user can create many posts
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    // An user can share many posts to another user and vice-versa
    @ManyToMany(cascade = {CascadeType.ALL, CascadeType.REMOVE})
    @JoinTable(
            name = "post_share",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private List<Post> sharedPosts = new ArrayList<>();
}
