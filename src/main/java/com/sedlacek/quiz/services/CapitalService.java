package com.sedlacek.quiz.services;

import com.sedlacek.quiz.models.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class CapitalService {

    public String renderContinentChoices(Model model) {
        model.addAttribute("isSomeoneLogged", User.isSomeoneLogged);
        return "geography-capitals";
    }
}
