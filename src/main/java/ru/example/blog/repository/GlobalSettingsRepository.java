package ru.example.blog.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.example.blog.model.GlobalSettings;

import java.util.List;

@Repository
public interface GlobalSettingsRepository extends CrudRepository<GlobalSettings, Integer> {

    /**
     * поиск всех настроек блога
     */
    List<GlobalSettings> findAll();

}
