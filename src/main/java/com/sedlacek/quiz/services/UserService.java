package com.sedlacek.quiz.services;

import com.sedlacek.quiz.models.*;
import com.sedlacek.quiz.repositories.LoginSessionRepository;
import com.sedlacek.quiz.repositories.UserRepository;
import org.hibernate.internal.util.StringHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final LoginSessionRepository loginSessionRepository;
    private ErrorMessage message = new ErrorMessage();
    private LoginSession loginSessionUser;


    public UserService(UserRepository userRepository, LoginSessionRepository loginSessionRepository) {
        this.userRepository = userRepository;
        this.loginSessionRepository = loginSessionRepository;
    }

    public String renderIndexPage(Model model) {
        if (loginSessionUser != null) {
            model.addAttribute("loggedUser", loginSessionUser.tryGetLoggedUser());
        }
            ErrorMessage.isError = false;
        return "index";
    }

    public String renderRegistrationPage(Model model) {
        if (loginSessionUser != null) {
            model.addAttribute("loggedUser", loginSessionUser.tryGetLoggedUser());
        }
            model.addAttribute("isError", ErrorMessage.isError);
            model.addAttribute("errorMessage", message.getMessage());
        return "registration";
    }

    public ResponseEntity<?> registerNewUser(@RequestBody RegisterUserDto user, Model model) {
        if (StringHelper.isBlank(user.getUsername()) || StringHelper.isBlank(user.getPassword())
                || StringHelper.isBlank(user.getEmail())) {
            var response = new RegisterUserResponse();
            response.setRegistered(false);
            response.setResponseMessage("Vyplňte všechna pole!");
            return ResponseEntity.badRequest().body(response);
        }
        if (!passwordConfirmation(user.getPassword(), model, user.getPasswordConfirm())) {
            var response = new RegisterUserResponse();
            response.setRegistered(false);
            response.setResponseMessage("Hesla se neshodují!");
            return ResponseEntity.badRequest().body(response);
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            var response = new RegisterUserResponse();
            response.setRegistered(false);
            response.setResponseMessage("Zadané uživatelské jméno je již registrované!");
            return ResponseEntity.badRequest().body(response);
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            var response = new RegisterUserResponse();
            response.setRegistered(false);
            response.setResponseMessage("Zadaný email je již registrován!");
            return ResponseEntity.badRequest().body(response);
        }
        var response = new RegisterUserResponse();
        response.setRegistered(true);
        response.setResponseMessage("Uživatel byl úspěšně zaregistrován.");
        User newUser = new User(user.getUsername(), user.getPassword(), user.getEmail());
        userRepository.save(newUser);
        return ResponseEntity.ok().body(response);
    }

    public boolean passwordConfirmation(String password, Model model, String passwordConfirm) {
        return password.equals(passwordConfirm);
    }

    public String renderLoginPage(Model model) {
        if (loginSessionUser != null) {
            model.addAttribute("loggedUser", loginSessionUser.tryGetLoggedUser());
        }
            model.addAttribute("isError", ErrorMessage.isError);
            model.addAttribute("errorMessage", message.getMessage());
        return "login";
    }

    public String loginUser(@ModelAttribute User user) {
        if (!userRepository.existsByUsername(user.getUsername()) ||
                !user.getPassword().equals(userRepository.findByUsername(user.getUsername()).getPassword())) {
            ErrorMessage.isError = true;
            message.setMessage("Špatně zadané uživatelské jméno nebo heslo!");
            return "redirect:/user/login";
        }
        ErrorMessage.isError = false;
        LoginSession loginSession = new LoginSession(userRepository.findByUsername(user.getUsername()));
        loginSessionUser = loginSessionRepository.save(loginSession);
        return "redirect:/";
    }

    public String logoutUser() {
        loginSessionRepository.delete(loginSessionUser);
        loginSessionUser = null;
        return "redirect:/";
    }

    public String detailsUser(Model model) {
        if (loginSessionUser != null) {
            User user = loginSessionUser.tryGetLoggedUser();
            model.addAttribute("loggedUser", user);
        }
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

    public User tryGetLoginSessionUser() {
        List<LoginSession> loginSessions = loginSessionRepository.findAll();
        User user = null;
        if (loginSessions.size() > 0) {
            user = loginSessions.get(0).getUser();
        }
        return user;
    }

    public void updateUserOnLoginSession(User user) {
        loginSessionUser.setUser(user);
    }

}
