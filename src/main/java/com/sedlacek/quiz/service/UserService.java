package com.sedlacek.quiz.service;

import com.sedlacek.quiz.dto.LoginResponseDto;
import com.sedlacek.quiz.dto.ResponseMessageDto;
import com.sedlacek.quiz.entity.EntityBase;
import com.sedlacek.quiz.model.ErrorMessage;
import com.sedlacek.quiz.model.LoginSession;
import com.sedlacek.quiz.entity.User;
import com.sedlacek.quiz.dto.UserDto;
import com.sedlacek.quiz.repository.LoginSessionRepository;
import com.sedlacek.quiz.repository.UserRepository;
import com.sun.istack.NotNull;
import org.hibernate.internal.util.StringHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final LoginSessionRepository loginSessionRepository;
    private final ErrorMessage message = new ErrorMessage();
    private LoginSession loginSessionUser;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private static final String REDIRECT = "redirect:/";
    private static final String REDIRECT_REGISTRATION = REDIRECT + "user/registration";
    private static final String LOGGED_USER = "loggedUser";


    public UserService(UserRepository userRepository, LoginSessionRepository loginSessionRepository) {
        this.userRepository = userRepository;
        this.loginSessionRepository = loginSessionRepository;
    }

    public String renderIndexPage(Model model) {
        if (loginSessionUser != null) {
            model.addAttribute(LOGGED_USER, loginSessionUser.tryGetLoggedUser());
        }
            ErrorMessage.isError = false;
        return "index";
    }

    public String renderRegistrationPage(Model model) {
        if (loginSessionUser != null) {
            model.addAttribute(LOGGED_USER, loginSessionUser.tryGetLoggedUser());
        }
            model.addAttribute("isError", ErrorMessage.isError);
            model.addAttribute("errorMessage", message.getMessage());
        return "registration";
    }

    public String registerNewUser(@ModelAttribute User user, Model model, String passwordConfirm) {
        if (StringHelper.isBlank(user.getUsername()) || StringHelper.isBlank(user.getPassword())
                || StringHelper.isBlank(user.getEmail())) {
            ErrorMessage.isError = true;
            message.setMessage("Vyplňte všechna pole!");
            return REDIRECT_REGISTRATION;
        }
        if (!passwordConfirmation(user, model, passwordConfirm)) {
            ErrorMessage.isError = true;
            message.setMessage("Hesla se neshodují!");
            return REDIRECT_REGISTRATION;
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            ErrorMessage.isError = true;
            message.setMessage("Zadané uživatelské jméno je již registrované!");
            return REDIRECT_REGISTRATION;
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            ErrorMessage.isError = true;
            message.setMessage("Zadaný email je již registrován!");
            return REDIRECT_REGISTRATION;
        }
        ErrorMessage.isError = false;
        User newUser = new User(user.getUsername(), user.getPassword(), user.getEmail());
        userRepository.save(newUser);
        return REDIRECT;
    }

    public boolean passwordConfirmation(@ModelAttribute User user, Model model, String passwordConfirm) {
        model.addAttribute("passwordConfirm", passwordConfirm);
        return user.getPassword().equals(passwordConfirm);
    }

    public String renderLoginPage(Model model) {
        if (loginSessionUser != null) {
            model.addAttribute(LOGGED_USER, loginSessionUser.tryGetLoggedUser());
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
            return REDIRECT + "user/login";
        }
        ErrorMessage.isError = false;
        LoginSession loginSession = new LoginSession(userRepository.findByUsername(user.getUsername()));
        loginSessionUser = loginSessionRepository.save(loginSession);
        return REDIRECT;
    }

    public String logoutUser() {
        loginSessionRepository.delete(loginSessionUser);
        loginSessionUser = null;
        return REDIRECT;
    }

    public String detailsUser(Model model) {
        if (loginSessionUser != null) {
            User user = loginSessionUser.tryGetLoggedUser();
            model.addAttribute(LOGGED_USER, user);
        }
            return "user-details";

    }

    public User tryGetLoginSessionUser() {
        List<LoginSession> loginSessions = loginSessionRepository.findAll();
        User user = null;
        if (!loginSessions.isEmpty()) {
            user = loginSessions.get(0).getUser();
        }
        return user;
    }

    public void updateUserOnLoginSession(User user) {
        loginSessionUser.setUser(user);
    }

    public ResponseEntity<ResponseMessageDto> registration(@NotNull UserDto userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            return ResponseEntity.badRequest().body(new ResponseMessageDto("Účet s tímto uživatelským jménem již existuje"));
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.badRequest().body(new ResponseMessageDto("Účet s tímto emailem již existuje"));
        }
        User user = EntityBase.convert(userDTO, User.class);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(new ResponseMessageDto("Uživatel " + userDTO.getUsername() + " úspěšně zaregistrován"));
    }

    public ResponseEntity<List<UserDto>> getAllUsersByExp() {
        List<User> users = userRepository.findAllByOrderByExpDesc();
        List<UserDto> userDtos = users.stream().map(user -> EntityBase.convert(user, UserDto.class)).toList();
        return ResponseEntity.ok(userDtos);
    }

    public ResponseEntity<LoginResponseDto> login(@NotNull UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername());
        if (user != null && encoder.matches(userDto.getPassword(), user.getPassword())) {
            UserDto responseUser = EntityBase.convert(user, UserDto.class);
            return ResponseEntity.ok(new LoginResponseDto(responseUser, "Přihlášení proběhlo úspěšně"));
        } else {
            return ResponseEntity.badRequest().body(new LoginResponseDto(null, "Špatné uživatelské jméno nebo heslo"));
        }
    }

}
