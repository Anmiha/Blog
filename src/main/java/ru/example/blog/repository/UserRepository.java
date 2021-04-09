package ru.example.blog.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.example.blog.model.UserEntity;


import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    /**
     * поиск юзера по имейлу
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * поиск юзера по коду для восстановления пароля
     */
    Optional<UserEntity> findByCode(String code);
}
