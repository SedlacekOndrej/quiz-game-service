package com.sedlacek.quiz.controllers;

import com.sedlacek.quiz.models.Answer;
import com.sedlacek.quiz.models.States;
import com.sedlacek.quiz.services.CapitalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/quiz/geography/capitals")
public class CapitalController {

    Map<String, String> continent;

    private final CapitalService capitalService;

    public CapitalController(CapitalService capitalService) {
        this.capitalService = capitalService;
    }

    @GetMapping(value={"", "/"})
    public String getCapitalChoices(Model model) {
        return capitalService.renderContinentChoices(model);
    }

    @GetMapping("/{chosenContinent}")
    public String getChosenCapitals(@PathVariable String chosenContinent, Model model) {
        switch (chosenContinent) {
            case "europe" -> continent = States.Europe;
            case "asia" -> continent = States.AsiaAndOceania;
            case "america" -> continent = States.NorthAndSouthAmerica;
            case "africa" -> continent = States.Africa;
            default -> continent = new HashMap<>();
        }
        return capitalService.renderCapitals(model, continent);
    }

    @PostMapping("/results")
    public String postAnswers(@ModelAttribute Answer answer) {
        return capitalService.postAnswers(answer);
    }

    @GetMapping("/results")
    public String getResults(Model model) {
        return capitalService.renderResults(model);
    }

}
