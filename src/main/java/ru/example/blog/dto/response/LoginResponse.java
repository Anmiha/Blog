package ru.example.blog.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponse {
    private boolean result;

    @JsonProperty("user")
    private UserLoginResponse user;

    private UserResponse userResponse;
    public LoginResponse() {
    }


}

