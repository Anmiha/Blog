package ru.example.blog.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.blog.dto.response.InitResponse;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    private final InitResponse initResponse;

    public ApiGeneralController(InitResponse initResponse) {
        this.initResponse = initResponse;
    }

    @GetMapping(value = "/init")
    public InitResponse initHeader() {
        return initResponse;
    }
}




























