package ru.example.blog.model;

import lombok.Data;
import ru.example.blog.model.enums.GlobalSetting;


import javax.persistence.*;

@Data
@Entity
@Table(name = "global_settings")
public class GlobalSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private GlobalSetting code;
    private String name;
    private Boolean value;

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
