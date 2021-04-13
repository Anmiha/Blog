package ru.example.blog.service;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import ru.example.blog.dto.request.PostCommentRequest;

public interface PostCommentService {
    ResponseEntity<?> writeComment(PostCommentRequest request, Errors errors);
}
