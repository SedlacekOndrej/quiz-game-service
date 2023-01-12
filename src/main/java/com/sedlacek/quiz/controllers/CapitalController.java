package com.sedlacek.quiz.controllers;

import com.sedlacek.quiz.models.Capital;
import com.sedlacek.quiz.models.States;
import com.sedlacek.quiz.services.CapitalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class CapitalController {

    private final CapitalService capitalService;

    public CapitalController(CapitalService capitalService) {
        this.capitalService = capitalService;
    }

    @GetMapping("/quiz/geography/capitals")
    public String getCapitalChoices(Model model) {
        return capitalService.renderContinentChoices(model);
    }

    @GetMapping("/quiz/geography/capitals/europe")
    public String getEuropeanCapitals(Model model) {
        Map<String, String> continent = States.Europe;
        return capitalService.renderCapitals(model, continent);
    }

    @PostMapping("/quiz/geography/capitals/results")
    public String postAnswers(@ModelAttribute Capital capital, Map<String, String> chosenContinent) {
        return capitalService.postAnswers(capital, chosenContinent);
    }

    @GetMapping("/quiz/geography/results")
    public String getResults(Model model) {
        return capitalService.renderResults(model);
    }

    @GetMapping("/quiz/geography/capitals/asia")
    public String getAsianAndOceanicCapitals(Model model) {
        Map<String, String> continent = States.AsiaAndOceania;
        return capitalService.renderCapitals(model, continent);
    }

}
