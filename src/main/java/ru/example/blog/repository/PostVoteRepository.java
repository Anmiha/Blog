package ru.example.blog.repository;


import org.springframework.data.repository.CrudRepository;
import ru.example.blog.model.Post;
import ru.example.blog.model.PostVote;
import ru.example.blog.model.User;

import java.util.Optional;

public interface PostVoteRepository extends CrudRepository<PostVote, Integer> {

    Optional<PostVote> findPostVoteByPostAndUser(Post post, User user);
}
