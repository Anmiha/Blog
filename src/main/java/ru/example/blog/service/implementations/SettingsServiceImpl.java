package ru.example.blog.service.implementations;

import org.springframework.stereotype.Service;
import ru.example.blog.dto.response.SettingsResponse;
import ru.example.blog.service.SettingsService;

@Service
public class SettingsServiceImpl implements SettingsService {


        public SettingsResponse getGlobalSettings() {
        SettingsResponse settingsResponse = new SettingsResponse();
        settingsResponse.setMultiuserMode(true);
        settingsResponse.setStatisticIsPublic(true);
        return settingsResponse;
    }
}
