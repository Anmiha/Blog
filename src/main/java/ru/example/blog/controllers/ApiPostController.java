package ru.example.blog.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.blog.dto.response.PostResponse;
import ru.example.blog.service.PostService;

@RestController
@RequestMapping(value = "/api/post")

public class ApiPostController {

    private final PostService postService;
    public ApiPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping()
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam Integer offset,
            @RequestParam Integer limit,
            @RequestParam(defaultValue = "recent") String mode) {
        PostResponse response = postService.getPosts(mode, PageRequest.of((int) offset / limit, limit));
        return ResponseEntity.ok(response);
    }



}
