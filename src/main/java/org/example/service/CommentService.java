package org.example.service;

import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.example.domain.CommentDTO;
import org.example.domain.UserDetailsDTO;
import org.example.domain.UserImageData;
import org.example.entity.Comment;
import org.example.entity.Post;
import org.example.entity.User;
import org.example.mapper.MapStructMapper;
import org.example.repository.CommentRepository;
import org.example.repository.PostRepository;
import org.example.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final MapStructMapper mapper;

    public void writeCommentForPost(UUID post_id, String comment_text){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByEmail(auth.getName()).get();
        Optional<Post> post = postRepository.findById(post_id);
        Comment comment = new Comment(user,post.get());
        comment.setText(comment_text);
        comment.setCreated_time(LocalDateTime.now());
        commentRepository.save(comment);
    }

    // Get top-level comments for a post
    public List<CommentDTO> viewCommentsInPost(UUID post_id){
        List<Comment> comments = commentRepository.getCommentsForPost(post_id);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment comment : comments){
            CommentDTO commentDTO = configureCommentCreatorImage(comment);
            commentDTO.setReplies(viewRepliesInComment(comment));
            commentDTO.setUpdated_time(calculateCommentCreatedTimeGap(comment.getCreated_time()));
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

    public List<CommentDTO> viewRepliesInComment(Comment comment){
        List<Comment> replies = commentRepository.getRepliesByComment(comment);
        List<CommentDTO> replyDtos = new ArrayList<>();
        for (Comment reply : replies){
            CommentDTO replyDTO = configureCommentCreatorImage(reply);
            replyDTO.setUpdated_time(calculateCommentCreatedTimeGap(reply.getCreated_time()));
            replyDtos.add(replyDTO);
        }
        return replyDtos;
    }

    private CommentDTO configureCommentCreatorImage(Comment comment){
        User creator = comment.getUser();
        CommentDTO commentDTO = mapper.commentDto(comment);
        // Mapper for comment creator image
        UserDetailsDTO creator_detail = mapper.userDetailsDto(creator);
        // Set creator image for post
        commentDTO.setCreator(creator_detail);
        return commentDTO;
    }

    private String calculateCommentCreatedTimeGap(LocalDateTime created_time){
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(created_time, now);
        long seconds = duration.getSeconds();

        if (seconds >= 31536000) {
            long years = seconds / 31536000;
            return years + " year(s) ago";
        } else if (seconds >= 2592000) {
            long months = seconds / 2592000;
            return months + " month(s) ago";
        } else if (seconds >= 86400) {
            long days = seconds / 86400;
            return days + " day(s) ago";
        } else if (seconds >= 3600) {
            long hours = seconds / 3600;
            return hours + " hour(s) ago";
        } else if (seconds >= 60) {
            long minutes = seconds / 60;
            return minutes + " minute(s) ago";
        } else {
            return seconds + " second(s) ago";
        }
    }
}
