package ru.example.blog.service.implementations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.blog.model.GlobalSettings;
import ru.example.blog.repository.GlobalSettingsRepository;
import ru.example.blog.service.GlobalSettingService;


import java.util.List;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Service
@Transactional(readOnly=true)
public class GlobalSettingServiceImpl implements GlobalSettingService {

    private final GlobalSettingsRepository globalSettingsRepository;

      public GlobalSettingServiceImpl(GlobalSettingsRepository globalSettingsRepository) {
        this.globalSettingsRepository = globalSettingsRepository;
    }

    @Override
    @Transactional(readOnly = false)
    public void createSetting(final GlobalSettings globalSettings) {
        final GlobalSettings byCode = globalSettingsRepository.findByCode(globalSettings.getCode());
        if (byCode != null) {
            globalSettings.setId(byCode.getId());
            globalSettings.setName(byCode.getName());
        }
        globalSettingsRepository.save(globalSettings);
    }


    @Override
    public List<GlobalSettings> findAll() {
        return (List<GlobalSettings>) globalSettingsRepository.findAll();
    }
}



