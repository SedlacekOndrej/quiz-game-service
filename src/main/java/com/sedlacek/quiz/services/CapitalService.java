package com.sedlacek.quiz.services;

import com.sedlacek.quiz.models.EuropeanCapitals;
import com.sedlacek.quiz.models.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CapitalService {

    EuropeanCapitals europeanCapitals = new EuropeanCapitals();
    List<String> states = List.of("Česká republika", "Slovensko", "Rakousko", "Německo", "Polsko", "Francie", "Itálie",
            "Velká Británie", "Rusko");

    public String renderContinentChoices(Model model) {
        model.addAttribute("isSomeoneLogged", User.isSomeoneLogged);
        return "geography-capitals";
    }

    public String renderEuropeanCapitals(Model model) {
        model.addAttribute("isSomeoneLogged", User.isSomeoneLogged);
        model.addAttribute("states", generateRandomStates());
        return "european-capitals";
    }

    public List<String> generateRandomStates() {
        Random random = new Random();
        List<String> generatedStates = new ArrayList<>();
        while (generatedStates.size() < 5) {
            String generatedState = states.get(random.nextInt(8));
            if (!generatedStates.contains(generatedState)) {
                generatedStates.add(generatedState);
            }
        }
        return generatedStates;
    }
}
