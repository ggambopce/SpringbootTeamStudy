package com.springboot.study.backend.domain.user.entity;

public class User{
  private  Long id;
  private String username;
  private String password;
  // 생성자
  public User(Long id, String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }
  // getter
  public Long getId(){ return this.id;}
  public String getUsername(){ return this.username;}
  public String getPassword() {return this.password;}
}