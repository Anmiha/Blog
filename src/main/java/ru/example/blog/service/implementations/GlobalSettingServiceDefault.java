package ru.example.blog.service.implementations;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.example.blog.dto.request.GlobalSettingsRequest;
import ru.example.blog.dto.response.GlobalSettingResponse;
import ru.example.blog.enums.GlobalSettings;
import ru.example.blog.model.GlobalSetting;
import ru.example.blog.repository.GlobalSettingRepository;
import ru.example.blog.service.GlobalSettingService;


import java.util.HashSet;

import static ru.example.blog.enums.GlobalSettings.Code.*;

@Service
@AllArgsConstructor
public class GlobalSettingServiceDefault implements GlobalSettingService {
    private final GlobalSettingRepository repository;

    @Override public ResponseEntity<GlobalSettingResponse> getAllSettings() {
        GlobalSettingResponse response = new GlobalSettingResponse();

        HashSet<GlobalSetting> settings = repository.selectGlobalSettings();

        for (GlobalSetting gs : settings) {
            boolean val = gs.getValue().getValue();
            if (gs.getCode() == STATISTICS_IS_PUBLIC) {
                response.setStatisticsIsPublic(val);
            } else if (gs.getCode() == MULTIUSER_MODE) {
                response.setMultisuserMode(val);
            } else if (gs.getCode() == POST_PREMODERATION) {
                response.setPostPremoderation(val);
            }
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private static GlobalSetting getDefaultSettings(){
        GlobalSetting gs = new GlobalSetting();
        gs.setValue(GlobalSettings.Value.YES);
        gs.setCode(STATISTICS_IS_PUBLIC);
        gs.setName(STATISTICS_IS_PUBLIC.getName());
        return gs;
    }

    public HashSet<GlobalSetting> getSiteSettings(){
        return repository.selectGlobalSettings();
    }

    @Override public void setGlobalSettings(GlobalSettingsRequest request) {
        updateSettings(MULTIUSER_MODE, request.getMultiuserMode());
        updateSettings(POST_PREMODERATION, request.getPostPremoderation());
        updateSettings(STATISTICS_IS_PUBLIC, request.getStatisticsIsPublic());
    }

    private void updateSettings(GlobalSettings.Code code, boolean valueToUpdate) {
        GlobalSettings.Value value = valueToUpdate ? GlobalSettings.Value.YES : GlobalSettings.Value.NO;
        GlobalSetting globalSetting = repository.findByCode(code).orElse(getDefaultSettings());
        if (!value.equals(globalSetting.getValue())){
            globalSetting.setValue(value);
            repository.save(globalSetting);
        }
    }
}
