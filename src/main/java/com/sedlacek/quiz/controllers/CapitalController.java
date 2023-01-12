package com.sedlacek.quiz.controllers;

import com.sedlacek.quiz.models.Capital;
import com.sedlacek.quiz.services.CapitalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        return capitalService.renderEuropeanCapitals(model);
    }

    @PostMapping("/quiz/geography/capitals/europe")
    public String postEuropeanCapitals(@ModelAttribute Capital capital) {
        return capitalService.postAnswers(capital);
    }

    @GetMapping("/quiz/geography/results")
    public String getResults(Model model) {
        return capitalService.renderResults(model);
    }
}
