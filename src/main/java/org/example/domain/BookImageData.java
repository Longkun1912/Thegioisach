package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.Book;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookImageData {
    private Book book;
    private String mimeType;
    private String base64EncodedImage;
}
