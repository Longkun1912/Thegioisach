package org.example.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.entity.Comment;
import org.example.entity.User;

import java.util.List;
import java.util.UUID;

@Data
public class PostDTO {
    @JsonProperty
    private UUID id;

    @JsonProperty
    private String post_id;

    @JsonProperty
    private String title;

    @JsonProperty
    private PostImageData postImageData;

    @JsonProperty
    private String content_text;

    @JsonProperty
    private String last_updated;

    @JsonProperty
    private float average_rate;

    @JsonProperty
    private List<CommentDTO> comments;

    @JsonProperty
    private UserDetailsDTO creator_detail;

    @JsonProperty
    private List<User> sharedBy;

}
