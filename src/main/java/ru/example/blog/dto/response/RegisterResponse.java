package ru.example.blog.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.example.blog.dto.response.base.ResultResponse;
import ru.example.blog.dto.response.type.RegisterErrorResponse;

@Data
public class RegisterResponse extends ResultResponse {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private RegisterErrorResponse errors;
}
