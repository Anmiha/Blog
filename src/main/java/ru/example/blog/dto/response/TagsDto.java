package ru.example.blog.dto.response;

import java.util.List;

public class TagsDto {

    private List<TagDto> tags;

    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(final List<TagDto> tags) {
        this.tags = tags;
    }

    public TagsDto(final List<TagDto> tags) {
        this.tags = tags;
    }

    public TagsDto() {
    }
}
