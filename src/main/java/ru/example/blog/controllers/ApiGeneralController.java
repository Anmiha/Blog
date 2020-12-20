package ru.example.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.blog.repository.PostRepository;


import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ApiGeneralController {


    @Autowired
    private PostRepository postRepository;


   @RequestMapping(value = "/init", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity initHeader() {
//
//        ResponseEntity responseEntity=new ResponseEntity();
//        responseEntity.

        return null;
    }
}