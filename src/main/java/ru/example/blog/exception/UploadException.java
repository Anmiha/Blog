package ru.example.blog.exception;

import lombok.Data;

import java.util.Map;

@Data
public class UploadException extends RuntimeException{
    private Map<String, Object> errors;

    public UploadException() {
        this.errors.put("image","Размер файла превышает допустимый размер");
    }

    public UploadException(Map<String,Object> errors) {
        this.errors = errors;
    }
}
