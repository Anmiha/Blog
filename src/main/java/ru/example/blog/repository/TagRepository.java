package ru.example.blog.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.example.blog.model.Post;
import ru.example.blog.model.Tag;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Integer> {

    Tag findByNameIgnoreCase(String name);

    List<Tag> findByPosts(Post post);

    @Query("SELECT t FROM Tag t LEFT JOIN t.posts p WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' GROUP BY t.name")
    List<Tag> findTagOfPublishedPosts();

}
