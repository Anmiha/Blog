package ru.example.blog.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.blog.dto.response.InitResponse;
import ru.example.blog.dto.response.SettingsResponse;
import ru.example.blog.service.SettingsService;
import ru.example.blog.service.TagService;


@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    private final InitResponse initResponse;
    private final SettingsService settingsService;
    private final TagService tagService;

    public ApiGeneralController(InitResponse initResponse, SettingsService settingsService, TagService tagService) {
        this.initResponse = initResponse;
        this.settingsService = settingsService;
        this.tagService = tagService;
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
//
//    @GetMapping("/tag")
//    public ResponseEntity<TagsDto> apiGetTag(@RequestParam(value = "query", defaultValue = "") String query) {
//        TagsDto tagsDto = new TagsDto(tagService.getAllTags(query));
//        return new ResponseEntity(tagsDto, HttpStatus.OK);
//    }

}




























