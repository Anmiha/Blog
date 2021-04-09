package ru.example.blog.service;


import ru.example.blog.model.UserEntity;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface UserService {

    void addUser(UserEntity user);

    Boolean isModerator(Integer userId);

    UserEntity findUserById(Integer userId);

}
