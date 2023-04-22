package com.sedlacek.quiz.services;

import com.sedlacek.quiz.models.Answer;
import com.sedlacek.quiz.models.User;
import com.sedlacek.quiz.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.*;

@Service
public class CapitalService {

    private Map<String, String> chosenContinent;
    private List<String> states;
    private final List<String> failedStates = new ArrayList<>();
    private List<String> answeredCapitals;
    private long score;
    private final UserService userService;
    private final UserRepository userRepository;

    private static final String LOGGED_USER = "loggedUser";

    public CapitalService(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public String renderContinentChoices(Model model) {
        model.addAttribute(LOGGED_USER, userService.tryGetLoginSessionUser());
        return "geography-capitals";
    }

    public String renderResults(Model model) {
        model.addAttribute(LOGGED_USER, userService.tryGetLoginSessionUser());
        model.addAttribute("score", score);
        model.addAttribute("failedStates", failedStates);
        model.addAttribute("chosenContinent", chosenContinent);
        model.addAttribute("answeredCapitals", answeredCapitals);
        model.addAttribute("states", states);
        return "results";
    }

    public String renderCapitals(Model model, Map<String, String> continent) {
        states = generateRandomStates(continent);
        chosenContinent = continent;
        model.addAttribute(LOGGED_USER, userService.tryGetLoginSessionUser());
        model.addAttribute("states", states);
        int statesIndex = 0;
        for (int i = 1; i < 11; i++) {
            model.addAttribute("answers" + i, generateAnswers(states.get(statesIndex), continent));
            statesIndex++;
        }
        return "capitals";
    }

    public List<String> generateRandomStates(Map<String, String> continent) {
        Random random = new Random();
        List<String> generatedStates = new ArrayList<>();
        List<String> statesFromChosenContinent = continent.keySet().stream().toList();
        while (generatedStates.size() < 10) {
            String generatedState = statesFromChosenContinent.get(random.nextInt(continent.size() - 1));
            if (!generatedStates.contains(generatedState)) {
                generatedStates.add(generatedState);
            }
        }
        return generatedStates;
    }

    public List<String> generateAnswers(String state, Map<String, String> continent) {
        Random random = new Random();
        List<String> generatedAnswers = new ArrayList<>();
        generatedAnswers.add(continent.get(state));
        List<String> capitals = continent.values().stream().toList();
        while (generatedAnswers.size() < 4) {
            String generatedAnswer = capitals.get(random.nextInt(continent.size() - 1));
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
        failedStates.clear();
        for (String state : states) {
            if (capitals.get(index) == null) {
                capitals.set(index, "");
            }
            if (rightAnswer(chosenContinent.get(state), capitals.get(index))) {
                score++;
            } else {
                failedStates.add(state);
            }
            index++;
        }
    }

    public String postAnswers(@ModelAttribute Answer answer) {
        answeredCapitals = answer.answeredCapitals();
        playTheQuiz(answeredCapitals);
        User user = userService.tryGetLoginSessionUser();
        user.addExp(score * 10);
        user.setLevel(userService.levelCheck(user));
        userService.updateUserOnLoginSession(userRepository.save(user));
        return "redirect:/quiz/geography/capitals/results";
    }
}
