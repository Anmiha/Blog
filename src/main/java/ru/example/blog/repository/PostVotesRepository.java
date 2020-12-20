package ru.example.blog.repository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.example.blog.model.PostVote;

import java.util.Optional;


@Repository
public interface PostVotesRepository extends CrudRepository<PostVote, Integer> {


}
