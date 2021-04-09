package ru.example.blog.service;

import ru.example.blog.model.GlobalSettings;

import java.util.List;


public interface SettingsService {

    void createSetting(final GlobalSettings globalSettings);
    List<GlobalSettings> findAll();
}
