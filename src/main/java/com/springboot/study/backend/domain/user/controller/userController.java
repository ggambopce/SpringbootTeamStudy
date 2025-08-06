package com.springboot.study.backend.global.auth.controller;

import org.springframework.web.bind.annotation.*;
import com.springboot.study.backend.domain.user.entity.User;


@RestController
public class UserController {

  @GetMapping("/")
    public String home() {
        return "Hello World!";
    }
}