package ru.example.blog.model;

import ru.example.blog.model.enums.Role;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int id;

    @Column(name = "is_moderator", columnDefinition = "TINYINT", nullable = false)
    private int isModerator;

    @Column(name = "reg_time", columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime regTime;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(length = 255)
    private String code;

    @Column(length = 65535, columnDefinition = "Text")
    private String photo;

    public Role gerRole() {
        return isModerator == 1 ? Role.MODERATOR : Role.USER;
    }

    public User() {
    }

    public User(int id, int isModerator, LocalDateTime regTime,
                String name, String email, String password,
                String code, String photo) {
        this.id = id;
        this.isModerator = isModerator;
        this.regTime = regTime;
        this.name = name;
        this.email = email;
        this.password = password;
        this.code = code;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsModerator() {
        return isModerator;
    }

    public void setIsModerator(int isModerator) {
        this.isModerator = isModerator;
    }

    public LocalDateTime getRegTime() {
        return regTime;
    }

    public void setRegTime(LocalDateTime regTime) {
        this.regTime = regTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
