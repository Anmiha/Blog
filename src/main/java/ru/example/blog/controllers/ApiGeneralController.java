package ru.example.blog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import ru.example.blog.dto.response.InitResponse;
import ru.example.blog.model.GlobalSettings;
import ru.example.blog.service.GlobalSettingService;
import ru.example.blog.service.SettingsService;
import ru.example.blog.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    private final InitResponse initResponse;
    private final ApiAuthController apiAuthController;
    private final UserService userService;
    private final GlobalSettingService globalSettingService;

    public ApiGeneralController(InitResponse initResponse, ApiAuthController apiAuthController,
                                UserService userService, GlobalSettingService globalSettingService) {
        this.initResponse = initResponse;
        this.apiAuthController = apiAuthController;
        this.userService = userService;
        this.globalSettingService = globalSettingService;
    }

    @GetMapping(value = "/init")
    public InitResponse initHeader() {
        return initResponse;
    }


    @GetMapping("/settings")
    public ResponseEntity<Map<String, Boolean>> apiGetSettings(HttpServletRequest request) {
        Integer userId = apiAuthController.getUserIdFromSession(request);
        if (userService.isModerator(userId)) {
            return new ResponseEntity(getSettings(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping(value = "/settings")
    public ResponseEntity<Map<String, Boolean>> apiPutSettings(HttpServletRequest request,
                                                               @RequestBody Map<String, Boolean> globalSettings)
            throws HttpMessageNotReadableException {
        Integer userId = apiAuthController.getUserIdFromSession(request);
        if (userService.isModerator(userId)) {
            //TODO Валидация входных данных ?
            for (Map.Entry<String, Boolean> entry : globalSettings.entrySet()) {
                globalSettingService.createSetting(
                        new GlobalSettings(entry.getKey(), entry.getValue().equals(true) ? "YES" : "NO")
                );
            }
            return new ResponseEntity(getSettings(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    private Map<String, Boolean> getSettings() {
        Map<String, Boolean> glSett = new HashMap<>();
        for (GlobalSettings glSettings : globalSettingService.findAll()) {
            glSett.put(glSettings.getCode(), glSettings.getValue().equals("YES") ? true : false);
        }
        return glSett;
    }


}




























