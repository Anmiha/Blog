package ru.example.blog.dto.projection;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface PidSumsVotesComment {
    @Value(value = "#{target.pid}")
    Integer getId();
    Integer getLikes();
    Integer getDislikes();
    Integer getCommentCount();
}
