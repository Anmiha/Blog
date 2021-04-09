package ru.example.blog.exception;

/**
 * @author alkarik
 * @link http://alkarik
 */
public class UserUnauthorizedException extends RuntimeException {
    public UserUnauthorizedException(final String message) {
        super(message);
    }
}
