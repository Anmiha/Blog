package ru.example.blog.dto.response;

import org.springframework.stereotype.Controller;
import ru.example.blog.model.enums.GlobalSetting;

@Controller
public class SettingsResponse {

    private Integer id;
    private GlobalSetting code;
    private String name;
    private Boolean value;


    public SettingsResponse() {
    }

    public SettingsResponse(GlobalSetting code, String name, Boolean value) {
        this.code = code;
        this.name = name;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GlobalSetting getCode() {
        return code;
    }

    public void setCode(GlobalSetting code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
}
