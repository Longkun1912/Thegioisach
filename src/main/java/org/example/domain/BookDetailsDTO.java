package org.example.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.entity.*;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookDetailsDTO {
    @JsonProperty
    private int id;

    @JsonProperty
    private String title;

    @JsonProperty
    private String author;

    @JsonProperty
    private LocalDate published_date;

    @JsonProperty
    private int page;

    @JsonProperty
    private String description;

    @JsonProperty
    private byte[] content;

    @JsonProperty
    private BookImageData bookImageData;

    @JsonProperty
    private int recommended_age;

    @JsonProperty
    private Category category;

    @JsonProperty
    private List<Favorite> favorites;

    @JsonProperty
    private List<Comment> comments;
}
