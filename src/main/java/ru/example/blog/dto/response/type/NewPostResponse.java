package ru.example.blog.dto.response.type;

import lombok.Data;

@Data
public class NewPostResponse {
    private String title;
    private String text;
}
