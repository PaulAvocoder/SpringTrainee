package com.galomzik.spring.controller;

import com.galomzik.spring.dto.*;
import com.galomzik.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/info") // обращаемся через info
public class UserController {
    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;


    @GetMapping("/getAllUsers")
    public ResponseEntity<String> getAllUsersFromDB() {
        return ResponseEntity.ok(userService.getAllUsers());
    } // делегирует запрос сервису

  @PostMapping("/signUpUser") //регистрация юзера
   public ResponseEntity<String> signUpUserForDB(@RequestBody UserDTO user) { // образует контейнер данных пользователя и передает сервису
       return ResponseEntity.status(201).body(userService.signUpUser(user));
   }

    @PostMapping("/signInUser") //вход юзера
    public ResponseEntity<String> signInUserForBD(@RequestBody UserDTO user) { // образует контейнер данных пользователя и передает сервису
        return ResponseEntity.ok(userService.signInUser(user));
    }

    @PostMapping("/changePassword") //смена пароля
    public ResponseEntity<String> changePasswordFromBD(@RequestBody ChangePswdDTO changePasswordDTO) { // образует контейнер данных пользователя и передает сервису
        return ResponseEntity.ok(userService.changePassword(changePasswordDTO));
    }

}
