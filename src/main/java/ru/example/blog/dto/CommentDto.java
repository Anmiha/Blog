package ru.example.blog.dto;


import lombok.Data;
import ru.example.blog.model.PostComment;

@Data
public class CommentDto {
    private long id;
    private long timestamp;
    private String text;
    private UserDto user;


    public CommentDto(PostComment postComment) {
        id = postComment.getId();
        timestamp = postComment.getTime().getEpochSecond();
        text = postComment.getText();
        user = new UserDto(postComment.getUser());
    }
}
