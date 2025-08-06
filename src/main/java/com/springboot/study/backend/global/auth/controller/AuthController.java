package com.springboot.study.backend.global.auth.controller;

import org.springframework.web.bind.annotation.*;
import com.springboot.study.backend.domain.user.entity.User;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @PostMapping("/login")
  public User login(@RequestBody User loginRequest) {
    //authService.login()
    return null;
  }
  
  @GetMapping("/logout")
  public void logout(){
    // authService.logout()
  }

}