package org.example.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class PostHandlingDTO {
    @JsonProperty
    private UUID id;

    @JsonProperty
    private String title;

    @JsonProperty
    private String content_text;

    @JsonProperty
    private MultipartFile image_file;
}
