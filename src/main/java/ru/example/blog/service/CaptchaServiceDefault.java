package ru.example.blog.service;


import org.springframework.http.ResponseEntity;
import ru.example.blog.dto.response.CaptchaResponse;
import ru.example.blog.model.CaptchaCode;

public interface CaptchaServiceDefault {
    CaptchaCode getCaptcha();

    ResponseEntity<CaptchaResponse> getCaptchaResponse();
}
