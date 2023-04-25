package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Comment;
import org.example.entity.Post;
import org.example.entity.User;
import org.example.repository.CommentRepository;
import org.example.repository.PostRepository;
import org.example.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void writeCommentForPost(UUID post_id, String comment_text){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByEmail(auth.getName()).get();
        Optional<Post> post = postRepository.findById(post_id);
        Comment comment = new Comment(user,post.get());
        comment.setText(comment_text);
        comment.setCreated_time(LocalDateTime.now());
        commentRepository.save(comment);
    }

    public List<Comment> viewCommentsInPost(UUID post_id){
        List<Comment> comments = commentRepository.getCommentsForPost(post_id);
        return comments;
    }
}
