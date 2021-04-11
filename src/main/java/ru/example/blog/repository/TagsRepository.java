package ru.example.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.example.blog.dto.projection.TagType;
import ru.example.blog.model.Tag;

import java.util.List;

@Repository
public interface TagsRepository extends JpaRepository<Tag,Integer> {
    Tag findTagsByName(String tagName);

    @Query(nativeQuery = true, value = "SELECT t1.id,t1.name, COUNT(t.tag_id) as cnt " +
        "FROM tag2post t " +
        "LEFT JOIN tags t1 ON t.tag_id = t1.id " +
        "GROUP BY t.tag_id ORDER BY cnt DESC")
    List<TagType> tagsCountInPost();


}
