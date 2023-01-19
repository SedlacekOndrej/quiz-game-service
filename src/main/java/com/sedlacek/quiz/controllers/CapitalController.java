package com.sedlacek.quiz.controllers;

import com.sedlacek.quiz.models.Capital;
import com.sedlacek.quiz.models.States;
import com.sedlacek.quiz.services.CapitalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class CapitalController {

    Map<String, String> continent;

    private final CapitalService capitalService;

    public CapitalController(CapitalService capitalService) {
        this.capitalService = capitalService;
    }

    @GetMapping("/quiz/geography/capitals")
    public String getCapitalChoices(Model model) {
        return capitalService.renderContinentChoices(model);
    }

    @GetMapping("/quiz/geography/capitals/{chosenContinent}")
    public String getChosenCapitals(@PathVariable String chosenContinent, Model model) {
        switch (chosenContinent) {
            case "europe" -> continent = States.Europe;
            case "asia" -> continent = States.AsiaAndOceania;
            case "america" -> continent = States.NorthAndSouthAmerica;
            case "africa" -> continent = States.Africa;
        }
        return capitalService.renderCapitals(model, continent);
    }

    @PostMapping("/quiz/geography/capitals/results")
    public String postAnswers(@ModelAttribute Capital capital) {
        return capitalService.postAnswers(capital);
    }

    @GetMapping("/quiz/geography/results")
    public String getResults(Model model) {
        return capitalService.renderResults(model);
    }

}
