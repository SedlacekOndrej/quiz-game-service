package com.sedlacek.quiz.controllers;

import com.sedlacek.quiz.models.RegisterUserDto;
import com.sedlacek.quiz.models.User;
import com.sedlacek.quiz.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getIndexPage(Model model) {
        return userService.renderIndexPage(model);
    }


    @PostMapping("/api/user/registration")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> userRegistration(@RequestBody RegisterUserDto user, Model model) {
        return userService.registerNewUser(user, model);
    }

    @GetMapping("/user/login")
    public String getLoginPage(Model model) {
        return userService.renderLoginPage(model);
    }

    @PostMapping("/api/user/login")
    public String userLogin(@ModelAttribute User user) {
        return userService.loginUser(user);
    }

    @GetMapping("/user/logout")
    public String userLogout() {
        return userService.logoutUser();
    }

    @GetMapping("/user/details")
    public String userDetails(Model model) {
        return userService.detailsUser(model);
    }
}
