package ru.example.blog.dto.response;


import ru.example.blog.dto.TagDto;

import java.util.List;

public class TagResponse {
    private List<TagDto> tags;

    public List<TagDto> getTags() {
        return tags;
    }

    public TagResponse(){

    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }

}
