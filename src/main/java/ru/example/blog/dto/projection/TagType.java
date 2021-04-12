package ru.example.blog.dto.projection;

import org.springframework.beans.factory.annotation.Value;

public interface TagType {
    Integer getId();

    String getName();
    @Value(value = "#{target.cnt}")
    Integer getCount();
}
