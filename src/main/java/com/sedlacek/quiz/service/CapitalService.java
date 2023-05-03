package com.sedlacek.quiz.service;

import com.sedlacek.quiz.dto.*;
import com.sedlacek.quiz.model.Answer;
import com.sedlacek.quiz.entity.User;
import com.sedlacek.quiz.model.States;
import com.sedlacek.quiz.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.*;

@Service
public class CapitalService {
    Random random = new Random();
    private Map<String, String> chosenContinent;
    private List<String> states;
    private List<String> failedStates;
    private List<String> succeededStates;
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
            model.addAttribute("answers" + i, generateQuestions(states.get(statesIndex), continent));
            statesIndex++;
        }
        return "capitals";
    }

    public List<String> generateRandomStates(Map<String, String> continent) {
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

    public List<String> generateQuestions(String state, Map<String, String> continent) {
        List<String> generatedQuestions = new ArrayList<>();
        generatedQuestions.add(continent.get(state));
        List<String> capitals = continent.values().stream().toList();
        while (generatedQuestions.size() < 4) {
            String generatedQuestion = capitals.get(random.nextInt(continent.size() - 1));
            if (!generatedQuestions.contains(generatedQuestion)) {
                generatedQuestions.add(generatedQuestion);
            }
        }
        Collections.shuffle(generatedQuestions);
        return generatedQuestions;
    }

    public boolean rightAnswer(String state, String capital) {
        return capital.equals(state);
    }

    private List<String> getFailedStates() {
        if (failedStates == null) {
            failedStates = new ArrayList<>();
        }
        return failedStates;
    }

    private List<String> getSucceededStates() {
        if (succeededStates == null) {
            succeededStates = new ArrayList<>();
        }
        return succeededStates;
    }

    public void playTheQuiz(List<String> capitals) {
        score = 0;
        int index = 0;
        getFailedStates().clear();
        getSucceededStates().clear();
        for (String state : states) {
            if (capitals.get(index) == null) {
                capitals.set(index, "");
            }
            if (rightAnswer(chosenContinent.get(state), capitals.get(index))) {
                succeededStates.add(state);
                score++;
            } else {
                failedStates.add(state);
            }
            index++;
        }
    }

    public void playTheQuizRest(AnswersDto answers, List<String> states, User user) {
        score = 0;
        int index = 0;
        failedStates.clear();
        succeededStates.clear();
        for (String state : states) {
            if (answers.getAnswers().get(index) == null) {
                answers.getAnswers().set(index, "");
            }
            if (rightAnswer(chosenContinent.get(state), answers.getAnswers().get(index))) {
                user.addRightAnswer();
                succeededStates.add(state);
                score++;
            } else {
                user.addWrongAnswer();
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
        userService.updateUserOnLoginSession(userRepository.save(user));
        return "redirect:/quiz/geography/capitals/results";
    }

    public ResponseEntity<QuestionsDto> getQuestions(Map<String, String> continent) {
        List<String> generatedStates = generateRandomStates(continent);
        List<String> generatedCities = new ArrayList<>();
        for (String state: generatedStates) {
            List<String> cities = generateQuestions(state, continent);
            generatedCities.addAll(cities);
        }
        return ResponseEntity.ok(new QuestionsDto(generatedStates, generatedCities));
    }

    public ResponseEntity<PlayingResponseDto> submitAnswers(StatesAndAnswersDto statesAndAnswers) {
        switch (statesAndAnswers.getContinent()) {
            case "europe" -> chosenContinent = States.Europe;
            case "asia" -> chosenContinent = States.AsiaAndOceania;
            case "america" -> chosenContinent = States.NorthAndSouthAmerica;
            case "africa" -> chosenContinent = States.Africa;
            default -> chosenContinent = new HashMap<>();
        }
        User user = userRepository.findByUsername(statesAndAnswers.getUsername());
        playTheQuizRest(statesAndAnswers.getAnswers(), statesAndAnswers.getStates(), user);
        user.addExp(score * 10);
        user.countPercentage();
        userRepository.save(user);
        return ResponseEntity.ok(new PlayingResponseDto(score, failedStates, succeededStates));
    }
}
