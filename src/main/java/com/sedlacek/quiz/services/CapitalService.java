package com.sedlacek.quiz.services;

import com.sedlacek.quiz.models.EuropeanCapitals;
import com.sedlacek.quiz.models.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service
public class CapitalService {

    EuropeanCapitals europeanCapitals = new EuropeanCapitals(new HashMap<>());
    List<String> europeanStates = List.of(
            "Albánie", "Andorra", "Arménie", "Ázerbájdžán", "Belgie", "Bělorusko", "Bosna a Hercegovina",
            "Bulharsko", "Černá Hora", "Česká republika", "Dánsko", "Estonsko", "Finsko", "Francie", "Gruzie",
            "Chorvatsko", "Irsko", "Island", "Itálie", "Kazachstán", "Kosovo", "Kypr", "Lichtenštejnsko", "Litva",
            "Lotyšsko", "Lucembursko", "Maďarsko", "Malta", "Moldavsko", "Monako", "Německo", "Nizozemsko", "Norsko",
            "Polsko", "Portugalsko", "Rakousko", "Rumunsko", "Rusko", "Řecko", "San Marino", "Severní Makedonie",
            "Slovensko", "Slovinsko", "Spojené království", "Srbsko", "Španělsko", "Švédsko", "Švýcarsko", "Turecko",
            "Ukrajina", "Vatikán"
    );

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
        while (generatedStates.size() < 10) {
            String generatedState = europeanStates.get(random.nextInt(50));
            if (!generatedStates.contains(generatedState)) {
                generatedStates.add(generatedState);
            }
        }
        return generatedStates;
    }

    public boolean rightAnswer(String state, String capital) {
        return capital.equals(state);
    }

    public void playTheQuiz(String capital) {
        List<String> states = generateRandomStates();
        long score = 0;
        for (String state : states) {
            if (rightAnswer(europeanCapitals.getCapitals().get(state), capital)) {
                score++;
            }
        }
        User.loggedUser.setExp(User.loggedUser.getExp() + (score * 10));
    }
}
