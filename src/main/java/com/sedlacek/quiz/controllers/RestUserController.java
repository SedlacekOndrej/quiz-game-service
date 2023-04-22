package com.sedlacek.quiz.controllers;

import com.sedlacek.quiz.dtos.ResponseMessageDto;
import com.sedlacek.quiz.dtos.UserDto;
import com.sedlacek.quiz.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class RestUserController {
    private final UserService userService;

    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<ResponseMessageDto> registerUser(@RequestBody UserDto userDTO) {
        return userService.registerUser(userDTO);
    }

    @GetMapping("/leaderboards")
    public ResponseEntity<List<UserDto>> getLeaderboards() {
        return userService.getAllUsersByExp();
    }
}
