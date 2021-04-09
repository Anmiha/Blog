package ru.example.blog.exception;

/**
 * @author alkarik
 * @link http://alkarik
 */
public class PostNotFoundResponse {
    private String postNotFound;

    public PostNotFoundResponse(final String postNotFound) {
        this.postNotFound = postNotFound;
    }

    public String getPostNotFound() {
        return postNotFound;
    }

    public void setPostNotFound(final String postNotFound) {
        this.postNotFound = postNotFound;
    }
}
