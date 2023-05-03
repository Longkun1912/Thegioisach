package org.example.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.entity.Post;
import org.example.entity.User;
import org.h2.engine.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentDTO {
    @JsonProperty
    private int id;

    @JsonProperty
    private String text;

    @JsonProperty
    private String updated_time;

    @JsonProperty
    private List<CommentDTO> replies;

    @JsonProperty
    private CommentDTO parent;

    @JsonProperty
    private UserDetailsDTO creator;
}
