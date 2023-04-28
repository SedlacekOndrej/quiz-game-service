package com.sedlacek.quiz.controller;

import com.sedlacek.quiz.dto.QuestionsDto;
import com.sedlacek.quiz.model.States;
import com.sedlacek.quiz.service.CapitalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/capitals")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class RestCapitalController {
    Map<String, String> continent;
    private final CapitalService capitalService;

    public RestCapitalController(CapitalService capitalService) {
        this.capitalService = capitalService;
    }

    @GetMapping("/{continent}")
    public ResponseEntity<QuestionsDto> getQuestions(@PathVariable (name = "continent") String chosenContinent) {
        switch (chosenContinent) {
            case "europe" -> continent = States.Europe;
            case "asia" -> continent = States.AsiaAndOceania;
            case "america" -> continent = States.NorthAndSouthAmerica;
            case "africa" -> continent = States.Africa;
            default -> continent = new HashMap<>();
        }
        return capitalService.getQuestions(continent);
    }
}
