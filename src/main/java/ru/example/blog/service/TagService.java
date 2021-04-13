package ru.example.blog.service;

import ru.example.blog.dto.response.TagResponse;
import ru.example.blog.model.Tag;
import java.util.List;

public interface TagService {
    TagResponse getTags();

    Tag saveNewTag(String name);

    void deletePrevTags(List<Tag> tagList);

    Tag findTagByName(String name);
}
