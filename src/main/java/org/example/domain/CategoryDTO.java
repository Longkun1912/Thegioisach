package org.example.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CategoryDTO {
    @JsonProperty
    private int id;

    @JsonProperty
    @NotEmpty(message = "Please type the category name.")
    private String name;
}
