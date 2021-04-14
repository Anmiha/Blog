package ru.example.blog.service;


import org.springframework.http.ResponseEntity;
import ru.example.blog.dto.request.GlobalSettingsRequest;
import ru.example.blog.dto.response.GlobalSettingResponse;
import ru.example.blog.model.GlobalSetting;

import java.util.HashSet;

public interface GlobalSettingService {
    ResponseEntity<GlobalSettingResponse> getAllSettings();

    void setGlobalSettings(GlobalSettingsRequest request);

    HashSet<GlobalSetting> getSiteSettings();

}
