package ru.example.blog.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.example.blog.model.GlobalSettings;


import java.util.List;

@Repository
public interface GlobalSettingsRepository extends CrudRepository<GlobalSettings, Integer> {

    /**
     * поиск всех настроек блога
     */
    List<GlobalSettings> findAll();

    @Modifying
    @Transactional
    @Query(value = "UPDATE global_settings SET value = :value WHERE code = :code", nativeQuery = true)
    void updateValue(@Param("value") Boolean value, @Param("code") String code);
}
