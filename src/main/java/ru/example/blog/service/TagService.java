package ru.example.blog.service;

import ru.example.blog.dto.response.TagDto;
import ru.example.blog.model.Tag;
import java.util.List;
import java.util.Set;

public interface TagService {
    Tag saveOrGetTag(String tagName);

    Set<Tag> collectTags(String[] tags);

    List<TagDto> getAllTags(String tagName);
}
