package org.example.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class BookDTO {
    @JsonProperty
    private int id;

    @JsonProperty
    @NotEmpty(message = "This field not be empty.")
    private String title;

    @JsonProperty
    private byte[] image;

    @JsonProperty
    @NotEmpty(message = "This field cannot be empty.")
    private String author;

    @JsonProperty
    @NotNull(message = "This field cannot be empty.")
    private LocalDate published_date;

    @JsonProperty
    @NotEmpty(message = "This field cannot be empty.")
    private int page;

    @JsonProperty
    @NotEmpty(message = "Please provide some description at least.")
    private String description;

    @JsonProperty
    private byte[] content;

    @JsonProperty
    @NotEmpty(message = "You must select a recommended age.")
    private int recommended_age;

    @JsonProperty
    @NotEmpty(message = "You must select a category.")
    private String category_name;
}
