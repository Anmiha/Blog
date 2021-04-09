package ru.example.blog.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.example.blog.model.GlobalSettings;
import ru.example.blog.model.enums.GlobalSetting;

@Repository
public interface GlobalSettingsRepository extends CrudRepository<GlobalSettings, Integer> {

    GlobalSettings findByCode(String code);
}
