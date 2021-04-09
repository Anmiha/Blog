package ru.example.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {



    @RequestMapping("/")
    public String index() {
        System.out.println();
        return "index";
    }
}