package org.example.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.security.validation.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
public class UserDTO {
    @JsonProperty
    private UUID id;

    @JsonProperty
    @Size(min = 2, max = 100, message = "Username name must be between 2 and 100 characters.")
    private String username;

    @JsonProperty
    private byte[] image;

    @JsonProperty
    @NotEmpty(message = "This field cannot be empty.")
    @Email
    private String email;

    @JsonProperty
    @NotEmpty(message = "This field cannot be empty.")
    private String phone_number;

    @JsonProperty
    @NotEmpty(message = "You must select a status.")
    private String status;

    @JsonProperty
    @ValidPassword
    @NotEmpty(message = "This field cannot be empty.")
    private String password;

    @JsonProperty
    @NotEmpty(message = "This field cannot be empty.")
    private String confirm_password;

    @JsonProperty
    @NotEmpty(message = "You must select a role.")
    private String input_role;
}
