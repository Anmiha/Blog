package ru.example.blog.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.example.blog.dto.request.PostCommentRequest;
import ru.example.blog.service.PostCommentService;

@RestController
@AllArgsConstructor
public class PostCommentController {

    private final PostCommentService postCommentService;

    @PostMapping("/api/comment")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> writeComment(@RequestBody PostCommentRequest request, Errors errors){
        return postCommentService.writeComment(request, errors);
    }
}
