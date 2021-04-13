package ru.example.blog.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.example.blog.dto.request.NewPostRequest;
import ru.example.blog.dto.response.PostResponse;
import ru.example.blog.dto.response.base.ResultResponse;
import ru.example.blog.dto.response.type.NewPostResponse;
import ru.example.blog.enums.ModerationStatus;
import ru.example.blog.enums.PostModerationStatus;
import ru.example.blog.enums.VoteType;
import ru.example.blog.service.PostService;
import ru.example.blog.service.PostVoteService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/post")

public class ApiPostController {

    private final PostService postService;
    private final PostVoteService postVoteService;

    public ApiPostController(PostService postService, PostVoteService postVoteService) {
        this.postService = postService;
        this.postVoteService = postVoteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showPostById(
            @PathVariable int id
    ) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @GetMapping("")
    //@PreAuthorize("hasAutority('user:moderate')")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam Integer offset,
            @RequestParam Integer limit,
            @RequestParam(defaultValue = "recent") String mode) {
        PostResponse response = postService.getPosts(mode, PageRequest.of((int) offset / limit, limit));
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ResultResponse<NewPostResponse>> addNewPost(
            @RequestBody @Valid NewPostRequest request,
            Errors errors
    ) {
        ResultResponse<NewPostResponse> response = postService.addNewPost(request, errors);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ResultResponse<NewPostResponse>> editPost(
            @PathVariable int id,
            @RequestBody @Valid NewPostRequest request,
            Errors errors
    ) {
        ResultResponse<NewPostResponse> response = postService.editPost(id, request, errors);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<PostResponse> findPostsByQuery(
            @RequestParam Integer offset,
            @RequestParam Integer limit,
            @RequestParam String query
    ) {
        PostResponse response = postService.findPostsByQuery(query, PageRequest.of((int) offset / limit, limit));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/byDate")
    public ResponseEntity<PostResponse> findPostsByDate(
            @RequestParam Integer offset,
            @RequestParam Integer limit,
            @RequestParam String date
    ) {
        PostResponse response = postService.findPostsByDate(date, PageRequest.of((int) offset / limit, limit));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/byTag")
    public ResponseEntity<PostResponse> findPostsByTag(
            @RequestParam Integer offset,
            @RequestParam Integer limit,
            @RequestParam String tag
    ) {
        PostResponse response = postService.findPostsByTag(tag, PageRequest.of((int) offset / limit, limit));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/moderation")
    @PreAuthorize("hasAuthority('user:moderate')")
    public ResponseEntity<PostResponse> findPostsForModeration(
            @RequestParam Integer offset,
            @RequestParam Integer limit,
            @RequestParam ModerationStatus status
    ) {
        PostResponse response = postService.findPostsForModeration(status, PageRequest.of((int) offset / limit, limit));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<PostResponse> findMyPosts(
            @RequestParam Integer offset,
            @RequestParam Integer limit,
            @RequestParam PostModerationStatus status
    ) {
        PostResponse response = postService.findMyPosts(status, PageRequest.of((int) offset / limit, limit));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{vote}")
    public ResponseEntity<ResultResponse<?>> vote(@PathVariable VoteType vote, @RequestBody Map<String, Integer> body) {
        boolean result = postVoteService.vote(vote, body.getOrDefault("post_id", 0));
        ResultResponse<?> response = new ResultResponse<>(result);
        return ResponseEntity.ok(response);
    }

}
