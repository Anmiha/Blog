package ru.example.blog.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RestorePasswordRequest {
    @NotBlank
    private String email;
}
