package com.sedlacek.quiz.services;

import com.sedlacek.quiz.models.Capital;
import com.sedlacek.quiz.models.EuropeanCapitals;
import com.sedlacek.quiz.models.User;
import com.sedlacek.quiz.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.*;

@Service
public class CapitalService {

    EuropeanCapitals europeanCapitals = new EuropeanCapitals(new HashMap<>());
    List<String> states = new ArrayList<>();
    long score;
    private final UserService userService;
    private final UserRepository userRepository;

    public CapitalService(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public String renderContinentChoices(Model model) {
        model.addAttribute("loggedUser", userService.tryGetLoginSessionUser());
        return "geography-capitals";
    }

    public String renderResults(Model model) {
        model.addAttribute("loggedUser", userService.tryGetLoginSessionUser());
        model.addAttribute("score", score);
        return "results";
    }

    public String renderEuropeanCapitals(Model model) {
        states = generateRandomStates();
        model.addAttribute("loggedUser", userService.tryGetLoginSessionUser());
        model.addAttribute("states", states);
        model.addAttribute("answers1", generateAnswers(states.get(0)));
        model.addAttribute("answers2", generateAnswers(states.get(1)));
        model.addAttribute("answers3", generateAnswers(states.get(2)));
        model.addAttribute("answers4", generateAnswers(states.get(3)));
        model.addAttribute("answers5", generateAnswers(states.get(4)));
        model.addAttribute("answers6", generateAnswers(states.get(5)));
        model.addAttribute("answers7", generateAnswers(states.get(6)));
        model.addAttribute("answers8", generateAnswers(states.get(7)));
        model.addAttribute("answers9", generateAnswers(states.get(8)));
        model.addAttribute("answers10", generateAnswers(states.get(9)));
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

    public List<String> generateAnswers(String state) {
        Random random = new Random();
        List<String> generatedAnswers = new ArrayList<>();
        generatedAnswers.add(europeanCapitals.getCapitals().get(state));
        List<String> capitals = europeanCapitals.getCapitals().values().stream().toList();
        while (generatedAnswers.size() < 4) {
            String generatedAnswer = capitals.get(random.nextInt(50));
            if (!generatedAnswers.contains(generatedAnswer)) {
                generatedAnswers.add(generatedAnswer);
            }
        }
        Collections.shuffle(generatedAnswers);
        return generatedAnswers;
    }

    public boolean rightAnswer(String state, String capital) {
        return capital.equals(state);
    }

    public void playTheQuiz(List<String> capitals) {
        score = 0;
        int index = 0;
        for (String state : states) {
            if (capitals.get(index) == null) {
                capitals.set(index, "");
            }
            if (rightAnswer(europeanCapitals.getCapitals().get(state), capitals.get(index))) {
                score++;
            }
            index++;
        }
    }

    public String postAnswers(@ModelAttribute Capital capital) {
        List<String> answeredCapitals = capital.answeredCapitals();
        playTheQuiz(answeredCapitals);
        User user = userService.tryGetLoginSessionUser();
        user.addExp(score * 10);
        user.setLevel(userService.levelCheck(user));
        userService.updateUserOnLoginSession(userRepository.save(user));
        return "redirect:/quiz/geography/results";
    }
}
