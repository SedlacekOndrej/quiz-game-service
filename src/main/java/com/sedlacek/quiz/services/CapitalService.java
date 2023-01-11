package com.sedlacek.quiz.services;

import com.sedlacek.quiz.models.EuropeanCapitals;
import com.sedlacek.quiz.models.LoginSession;
import com.sedlacek.quiz.models.User;
import com.sedlacek.quiz.repositories.LoginSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

@Service
public class CapitalService {

    EuropeanCapitals europeanCapitals = new EuropeanCapitals(new HashMap<>());
    private final UserService userService;

    public CapitalService(UserService userService) {
        this.userService = userService;
    }

    public String renderContinentChoices(Model model) {
        model.addAttribute("loggedUser", userService.tryGetLoginSessionUser());
        return "geography-capitals";
    }

    public String renderEuropeanCapitals(Model model) {
        model.addAttribute("loggedUser", userService.tryGetLoginSessionUser());
        model.addAttribute("states", generateRandomStates());
        return "european-capitals";
    }

    public List<String> generateRandomStates() {
        Random random = new Random();
        List<String> generatedStates = new ArrayList<>();
        List<String> states = europeanCapitals.getCapitals().keySet().stream().toList();
        while (generatedStates.size() < 10) {
            String generatedState = states.get(random.nextInt(50));
            if (!generatedStates.contains(generatedState)) {
                generatedStates.add(generatedState);
            }
        }
        return generatedStates;
    }

    public boolean rightAnswer(String state, String capital) {
        return capital.equals(state);
    }

    public void playTheQuiz(String capital, User user) {
        List<String> states = generateRandomStates();
        long score = 0;
        for (String state : states) {
            if (rightAnswer(europeanCapitals.getCapitals().get(state), capital)) {
                score++;
            }
        }
        user.setExp(user.getExp() + (score * 10));
    }
}
