package ru.example.blog.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.example.blog.model.Tag;


@Repository
public interface TagRepository extends CrudRepository<Tag, Integer> {
    /**
     * поиск тега по значению
     */
    Tag findByName(String name);
}
