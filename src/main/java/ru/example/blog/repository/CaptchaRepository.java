package ru.example.blog.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.example.blog.model.CaptchaCode;


@Repository
public interface CaptchaRepository extends CrudRepository<CaptchaCode, Integer> {



}
