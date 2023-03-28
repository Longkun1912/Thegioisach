package org.example.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDetailsDTO {
    @JsonProperty
    private UUID id;

    @JsonProperty
    private String username;

    @JsonProperty
    private UserImageData userImageData;

    @JsonProperty
    private String email;

    @JsonProperty
    private String phone_number;

    @JsonProperty
    private String status;

    @JsonProperty
    private String last_updated;

    @JsonProperty
    private String password;
}
