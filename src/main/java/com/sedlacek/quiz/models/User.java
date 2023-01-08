package com.sedlacek.quiz.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private int level;
    private Long exp;

    public static boolean isSomeoneLogged = false;
    @Transient
    public static User loggedUser = null;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.level = 1;
        this.exp = 0L;
    }

    public User() {

    }
}
