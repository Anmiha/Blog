package ru.example.blog.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.example.blog.model.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {


}