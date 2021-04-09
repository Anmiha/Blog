package ru.example.blog.exception;

/**
 * @author alkarik
 * @link http://alkarik
 */
public class PostException extends RuntimeException {
    public PostException(final String message) {
        super(message);
    }
}
