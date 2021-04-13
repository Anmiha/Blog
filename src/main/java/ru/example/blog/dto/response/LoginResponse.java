package ru.example.blog.dto.response;



import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoginResponse {
    private boolean result;
    private UserResponse user;
}
