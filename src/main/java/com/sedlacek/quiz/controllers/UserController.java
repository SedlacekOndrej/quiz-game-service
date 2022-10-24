package com.sedlacek.quiz.controllers;

import com.sedlacek.quiz.models.User;
import com.sedlacek.quiz.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getIndexPage(Model model) {
        return userService.renderIndexPage(model);
    }

    @GetMapping("/user/registration")
    public String getRegistrationPage(Model model) {
        return userService.renderRegistrationPage(model);
    }

    @PostMapping("/user/registration")
    public String userRegistration(@ModelAttribute User user, Model model, String passwordConfirm) {
        return userService.registerNewUser(user, model, passwordConfirm);
    }

    @GetMapping("/user/login")
    public String getLoginPage(Model model) {
        return userService.renderLoginPage(model);
    }

    @PostMapping("/user/login")
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
