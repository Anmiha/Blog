package ru.example.blog.dto.response;

import lombok.Data;
import ru.example.blog.dto.CommentDto;
import ru.example.blog.dto.UserDto;
import ru.example.blog.model.Post;

import java.util.List;

@Data
public class PostWithCommentsResponse {
    private long id;
    private long timestamp;
    private boolean active;
    private UserDto user;
    private String title;
    private String text;
    private long likeCount;
    private long dislikeCount;
    private int viewCount;
    private List<CommentDto> comments;
    private List<String> tags;

    public PostWithCommentsResponse(Post post, List<CommentDto> postComments, List<String> postTags){
        id = post.getId();
        timestamp = post.getTime().getEpochSecond();
        active = post.isActive();
        user = new UserDto(post.getUser());
        title = post.getTitle();
        text = post.getText();
        likeCount = post.getPostVotes().stream().filter(postVote -> postVote.getValue() > 0).count();
        dislikeCount = post.getPostVotes().stream().filter(postVote -> postVote.getValue() < 0).count();
        viewCount = post.getViewCount();
        comments = postComments;
        tags = postTags;
    }
}
