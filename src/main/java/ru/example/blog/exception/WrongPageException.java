package ru.example.blog.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WrongPageException extends RuntimeException {
    private String text;
}
