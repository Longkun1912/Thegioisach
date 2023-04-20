package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.entity.Post;

@Data
@AllArgsConstructor
public class PostImageData {
    private Post post;
    private String mimeType;
    private String base64EncodedImage;


}
