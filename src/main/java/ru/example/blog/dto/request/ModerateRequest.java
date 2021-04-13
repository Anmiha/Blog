package ru.example.blog.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ModerateRequest {
    @JsonProperty("post_id")
    private int postId;
    private String decision;
}
