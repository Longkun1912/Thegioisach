package org.example.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.entity.Role;
import org.example.security.validation.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserRegisterDTO {
    @JsonProperty
    @Size(min = 2, max = 100, message = "Username name must be between 2 and 100 characters.")
    private String username;

    @JsonProperty
    private String image;

    @JsonProperty
    @NotEmpty(message = "This field cannot be empty.")
    @Email
    private String email;

    @JsonProperty
    @NotEmpty(message = "This field cannot be empty.")
    private String phone_number;

    @JsonProperty
    @ValidPassword
    @NotEmpty(message = "This field cannot be empty.")
    private String password;

    @JsonProperty
    @NotEmpty(message = "This field cannot be empty.")
    private String confirm_password;
}
