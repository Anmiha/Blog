package ru.example.blog.service;


import ru.example.blog.model.GlobalSettings;

import java.util.List;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface GlobalSettingService {
    void createSetting(final GlobalSettings globalSettings);
    List<GlobalSettings> findAll();
}
