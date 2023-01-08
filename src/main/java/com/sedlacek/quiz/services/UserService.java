package com.sedlacek.quiz.services;

import com.sedlacek.quiz.models.ErrorMessage;
import com.sedlacek.quiz.models.User;
import com.sedlacek.quiz.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private ErrorMessage message = new ErrorMessage();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String renderIndexPage(Model model) {
        model.addAttribute("isSomeoneLogged", User.isSomeoneLogged);
        model.addAttribute("loggedUser", User.loggedUser);
        ErrorMessage.isError = false;
        return "index";
    }

    public String renderRegistrationPage(Model model) {
        model.addAttribute("isSomeoneLogged", User.isSomeoneLogged);
        model.addAttribute("loggedUser", User.loggedUser);
        model.addAttribute("isError", ErrorMessage.isError);
        model.addAttribute("errorMessage", message.getMessage());
        return "registration";
    }

    public String registerNewUser(@ModelAttribute User user, Model model, String passwordConfirm) {
        if (emptyUsername(user) || emptyPassword(user) || emptyEmail(user)) {
            ErrorMessage.isError = true;
            message.setMessage("Vyplňte všechna pole!");
            return "redirect:/user/registration";
        }
        if (!passwordConfirmation(user, model, passwordConfirm)) {
            ErrorMessage.isError = true;
            message.setMessage("Hesla se neshodují!");
            return "redirect:/user/registration";
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            ErrorMessage.isError = true;
            message.setMessage("Zadané uživatelské jméno je již registrované!");
            return "redirect:/user/registration";
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            ErrorMessage.isError = true;
            message.setMessage("Zadaný email je již registrován!");
            return "redirect:/user/registration";
        }
        ErrorMessage.isError = false;
        User newUser = new User(user.getUsername(), user.getPassword(), user.getEmail());
        userRepository.save(newUser);
        return "redirect:/";
    }

    public boolean passwordConfirmation(@ModelAttribute User user, Model model, String passwordConfirm) {
        model.addAttribute("passwordConfirm", passwordConfirm);
        return user.getPassword().equals(passwordConfirm);
    }

    public String renderLoginPage(Model model) {
        model.addAttribute("isSomeoneLogged", User.isSomeoneLogged);
        model.addAttribute("loggedUser", User.loggedUser);
        model.addAttribute("isError", ErrorMessage.isError);
        model.addAttribute("errorMessage", message.getMessage());
        return "login";
    }

    public String loginUser(@ModelAttribute User user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            ErrorMessage.isError = true;
            message.setMessage("Zadané uživatelské jméno neexistuje!");
            return "redirect:/user/login";
        }
        if (!user.getPassword().equals(userRepository.findByUsername(user.getUsername()).getPassword())) {
            ErrorMessage.isError = true;
            message.setMessage("Špatné heslo!");
            return "redirect:/user/login";
        }
        ErrorMessage.isError = false;
        User.isSomeoneLogged = true;
        User.loggedUser = userRepository.findByUsername(user.getUsername());
        return "redirect:/";
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

    public boolean emptyUsername(User user) {
        return user.getUsername().equals("");
    }

    public boolean emptyPassword(User user) {
        return user.getPassword().equals("");
    }

    public boolean emptyEmail(User user) {
        return user.getEmail().equals("");
    }
}
