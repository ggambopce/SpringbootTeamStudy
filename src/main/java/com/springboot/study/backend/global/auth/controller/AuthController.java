package global.auth.controller;

import org.springframework.web.bind.annotation.*;
import domain.user.entity.User;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @PostMapping("/login")
  public User login(@RequestBody) {
    //authService.login()
  }
  
  @GetMapping("/logout")
  public void logout(){
    // authService.logout()
  }

}