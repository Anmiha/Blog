package ru.example.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author alkarik
 * @link http://alkarik
 */
@ControllerAdvice
public class PostAdvice {
    @ResponseBody
    @ExceptionHandler(PostException.class)
    public final ResponseEntity<PostNotFoundResponse> postNotFoundResponseEntity(PostException ex) {
        PostNotFoundResponse response = new PostNotFoundResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
