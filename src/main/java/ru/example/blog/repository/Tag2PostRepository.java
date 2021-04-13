package ru.example.blog.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.example.blog.model.Tag2Post;

@Repository
public interface Tag2PostRepository extends CrudRepository<Tag2Post, Integer> {


}
