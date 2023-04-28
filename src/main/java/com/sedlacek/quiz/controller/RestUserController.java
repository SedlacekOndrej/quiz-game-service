package com.sedlacek.quiz.controller;

import com.sedlacek.quiz.dto.ResponseDto;
import com.sedlacek.quiz.dto.ResponseMessageDto;
import com.sedlacek.quiz.dto.UserDto;
import com.sedlacek.quiz.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class RestUserController {
    private final UserService userService;

    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<ResponseMessageDto> registerUser(@RequestBody UserDto userDto) {
        return userService.registration(userDto);
    }

    @GetMapping("/leaderboards")
    public ResponseEntity<List<UserDto>> getLeaderboards() {
        return userService.getAllUsersByExp();
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> loginUser(@RequestBody UserDto userDto) {
        return userService.login(userDto);
    }
}
