package ru.example.blog.dto.response;

import lombok.Data;

@Data
public class UserLoginResponse {

    private long id;
    private String name;
    private String photo;
    private String email;
    private boolean moderation;
    private int moderationCount;
    private boolean settings;

}
