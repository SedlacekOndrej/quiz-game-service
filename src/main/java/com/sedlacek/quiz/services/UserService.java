package com.sedlacek.quiz.services;

import com.sedlacek.quiz.models.User;
import com.sedlacek.quiz.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String renderIndexPage(Model model) {
        model.addAttribute("isSomeoneLogged", User.isSomeoneLogged);
        model.addAttribute("loggedUser", User.loggedUser);
        return "index";
    }

    public String renderRegistrationPage(Model model) {
        model.addAttribute("isSomeoneLogged", User.isSomeoneLogged);
        model.addAttribute("loggedUser", User.loggedUser);
        return "registration";
    }

    public String registerNewUser(@ModelAttribute User user, Model model, String passwordConfirm) {
        if (passwordConfirmation(user, model, passwordConfirm)) {
            User newUser = new User(user.getUsername(), user.getPassword(), user.getEmail());
            userRepository.save(newUser);
            return "redirect:/";
        }
        return "redirect:/user/registration";
    }

    public boolean passwordConfirmation(@ModelAttribute User user, Model model, String passwordConfirm) {
        model.addAttribute("passwordConfirm", passwordConfirm);
        return user.getPassword().equals(passwordConfirm);
    }

    public String renderLoginPage(Model model) {
        model.addAttribute("isSomeoneLogged", User.isSomeoneLogged);
        model.addAttribute("loggedUser", User.loggedUser);
        return "login";
    }

    public String loginUser(@ModelAttribute User user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            return "redirect:/user/login";
        }
        if (user.getPassword().equals(userRepository.findByUsername(user.getUsername()).getPassword())) {
            User.isSomeoneLogged = true;
            User.loggedUser = userRepository.findByUsername(user.getUsername());
            return "redirect:/";
        }
        return "redirect:/user/login";
    }

    public String logoutUser() {
        User.isSomeoneLogged = false;
        User.loggedUser = null;
        return "redirect:/";
    }

    public String detailsUser(Model model) {
        User user = User.loggedUser;
        user.setLevel(levelCheck(User.loggedUser));
        model.addAttribute("isSomeoneLogged", User.isSomeoneLogged);
        model.addAttribute("loggedUser", user);
        return "user-details";
    }

    public int levelCheck(User user) {
        List<Integer> expNeeded = new ArrayList<>(List.of(50));

        for (int i = 0; i < 20; i++) {
            expNeeded.add(expNeeded.get(i) * 2);
        }

        for (int i = expNeeded.size(); i > 0; i--) {
            if (user.getExp() / expNeeded.get(i-1) > 0) {
                return expNeeded.indexOf(expNeeded.get(i))+1;
            }
        }
        return 1;
    }
}
