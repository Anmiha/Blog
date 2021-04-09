package ru.example.blog.service.implementations;

import org.springframework.stereotype.Service;
import ru.example.blog.model.UserEntity;
import ru.example.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public void addUser(UserEntity user) {

    }

    @Override
    public Boolean isModerator(Integer userId) {
        return null;
    }

    @Override
    public UserEntity findUserById(Integer userId) {
        return null;
    }
}
