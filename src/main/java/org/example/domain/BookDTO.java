package org.example.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class BookDTO {
    @JsonProperty
    private int id;

    @JsonProperty
    @NotEmpty(message = "This field not be empty.")
    private String title;

    @JsonProperty
    private MultipartFile image_file;

    @JsonProperty
    @NotEmpty(message = "This field cannot be empty.")
    private String author;

    @JsonProperty
    @NotEmpty(message = "Please select the published date.")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private String published_date;

    @JsonProperty
    @Min(value = 1, message = "Page number must be greater than or equal to 1.")
    @Max(value = 1000, message = "Page number must be less than or equal to 1000.")
    private int page;

    @JsonProperty
    @NotEmpty(message = "Please provide some description at least.")
    private String description;

    @JsonProperty
    private MultipartFile content_file;

    @JsonProperty
    @NotEmpty(message = "You must select a recommended age.")
    private String recommended_age;

    @JsonProperty
    @NotEmpty(message = "You must select a category.")
    private String category_name;
}
