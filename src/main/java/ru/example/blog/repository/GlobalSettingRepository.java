package ru.example.blog.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.example.blog.enums.GlobalSettings;
import ru.example.blog.model.GlobalSetting;

import java.util.HashSet;
import java.util.Optional;


public interface GlobalSettingRepository
        extends CrudRepository<GlobalSetting, Integer> {
    Optional<GlobalSetting> findByCode(GlobalSettings.Code code);

    @Query("SELECT gs FROM GlobalSetting gs")
    HashSet<GlobalSetting> selectGlobalSettings();
}
