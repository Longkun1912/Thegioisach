package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserImageData {
    private User user;
    private String mimeType;
    private String base64EncodedImage;
}
