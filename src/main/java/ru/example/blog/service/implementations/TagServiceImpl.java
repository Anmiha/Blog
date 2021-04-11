package ru.example.blog.service.implementations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.blog.dto.projection.TagType;
import ru.example.blog.dto.response.TagDto;
import ru.example.blog.model.Tag;
import ru.example.blog.repository.TagsRepository;
import ru.example.blog.service.TagService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {


    private final TagsRepository tagsRepository;

    public TagServiceImpl(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    @Override
    @Transactional(readOnly = false)
    public Tag saveOrGetTag(final String tagName) {
        Tag tagsByName = tagsRepository.findTagsByName(tagName);
        if (tagsByName == null) {
            tagsByName = tagsRepository.save(new Tag(tagName));
        }
        return tagsByName;
    }

    @Override
    public Set<Tag> collectTags(String[] tags) {
        Set<Tag> setTags = new HashSet<>();
        for (String splitTag : tags) {
            Tag ta = saveOrGetTag(splitTag);
            setTags.add(ta);
        }
        return setTags;
    }

    @Override
    public List<TagDto> getAllTags(final String tagName) {
        final List<TagType> tags = tagsRepository.tagsCountInPost();
        Boolean fistRow = true;
        Integer maxCount = 1;
        List<TagDto> tagsDto = new ArrayList<>();

        for (TagType tag : tags) {
            if (fistRow) {
                fistRow = false;
                maxCount = tag.getCount();
            }
            Float normWeight = (float) tag.getCount() / maxCount;
            final String nameTag = tag.getName();
            final Integer tagId = tag.getId();
            if (tagName.equals("")) {
                tagsDto.add(new TagDto(tagId, nameTag, normWeight));
            } else {
                if (nameTag.startsWith(tagName)) {
                    tagsDto.add(new TagDto(tagId, nameTag, normWeight));
                    break;
                }
            }
        }
        return tagsDto;
    }
}
