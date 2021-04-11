package ru.example.blog.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.blog.dto.response.InitResponse;
import ru.example.blog.dto.response.SettingsResponse;
import ru.example.blog.service.SettingsService;


@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    private final InitResponse initResponse;
    private final SettingsService settingsService;

    public ApiGeneralController(InitResponse initResponse, SettingsService settingsService) {
        this.initResponse = initResponse;
        this.settingsService = settingsService;
    }


    @GetMapping(value = "/init")
    public ResponseEntity<InitResponse> initHeader() {
        return ResponseEntity.ok(initResponse);
    }

    @GetMapping("/settings")
   // @PreAuthorize("hasAutority('user:moderate')")
    private ResponseEntity<SettingsResponse> settings() {
          return ResponseEntity.ok(settingsService.getGlobalSettings());
    }

    }




























