package ru.example.blog.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.example.blog.dto.CalendarDto;
import ru.example.blog.dto.request.GlobalSettingsRequest;
import ru.example.blog.dto.request.ModerateRequest;
import ru.example.blog.dto.response.GlobalSettingResponse;
import ru.example.blog.dto.response.StatisticResponse;
import ru.example.blog.dto.response.TagResponse;
import ru.example.blog.dto.response.base.ResultResponse;
import ru.example.blog.enums.StatisticsType;
import ru.example.blog.exception.UploadException;
import ru.example.blog.service.*;

import java.io.File;
import java.nio.file.Path;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ApiGeneralController {
    private final TagService tagService;
    private final PostService postService;
    private final GlobalSettingService globalSettingService;
    private final StatisticService statisticService;
    private final StorageService storageService;

    @GetMapping("/tag")
    public ResponseEntity<TagResponse> showTags(@RequestParam(required = false) String query) {
        return new ResponseEntity<>(tagService.getTags(), HttpStatus.OK);
    }

    @GetMapping("/calendar")
    public ResponseEntity<CalendarDto> showCalendar(@RequestParam(required = false) Integer year) {
        return new ResponseEntity<CalendarDto>(postService.getCalendar(year), HttpStatus.OK);
    }

    @GetMapping("/statistics/{type}")
    public ResponseEntity<StatisticResponse> showStatistics(@PathVariable StatisticsType type) {
        return statisticService.getStatistics(type);
    }

    @PutMapping("/settings")
    @PreAuthorize("hasAuthority('user:moderate')")
    public void setGlobalSettings(@RequestBody GlobalSettingsRequest request) {
        globalSettingService.setGlobalSettings(request);
    }

    @GetMapping("/settings")
    public ResponseEntity<GlobalSettingResponse> getGlobalSettings() {
        GlobalSettingResponse response = new GlobalSettingResponse();
        return globalSettingService.getAllSettings();
    }


    @PostMapping("/image")
    @PreAuthorize("hasAuthority('user:write')")
    public String handleFileUpload(@RequestParam MultipartFile image) {
        if (image.isEmpty()) {
            throw new UploadException();
        }

        return storageService.handleFileUpload(image);
    }

    @GetMapping(value = "/upload/{path1}/{path2}/{path3}/{fileName}")
    public @ResponseBody
    byte[] getImage(@PathVariable String path1,
                    @PathVariable String path2,
                    @PathVariable String path3,
                    @PathVariable String fileName) {
        String route;

        route = new File("").getAbsolutePath()
                .concat("/upload/" + path1 + "/" + path2 + "/" + path3 + "/" + fileName);

        return storageService.getImage(Path.of(route));
    }

    @PostMapping("/moderation")
    @PreAuthorize("hasAuthority('user:moderate')")
    public ResponseEntity<ResultResponse<?>> moderatePost(@RequestBody ModerateRequest request) {
        return ResponseEntity.ok(new ResultResponse<>(postService.moderatePost(request)));
    }

}
