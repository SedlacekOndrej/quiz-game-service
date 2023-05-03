package com.sedlacek.quiz.entity;

import com.sedlacek.quiz.model.LoginSession;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
public class User extends EntityBase {
    private String username;
    private String password;
    private String email;
    private int level;
    private long exp;
    private int rightAnswers;
    private int wrongAnswers;
    private double percentage;
    @OneToMany(mappedBy = "user")
    private List<LoginSession> loginSessions;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.level = 1;
        this.exp = 0L;
        this.rightAnswers = 0;
        this.wrongAnswers = 0;
        this.percentage = 0.00;
    }

    public void addExp(long exp) {
        // Add new experience to the player.
        this.exp += exp;

        while (this.exp >= calculateExpNeededForNextLevel(level)) {
            level++;
        }
    }

    // Calculate how much experience player needs for leveling up from current level with a little help from ChatGPT
    // "Hi, what do numbers 0, 10, 20, 40, 80, 160, 320, 640, 1280, 2560, 5120, 10240, 20480, 40960, 81920, 163840, 327680, 655360, 1310720, 2621440 have in common?"
    private int calculateExpNeededForNextLevel(int level) {
        return (int)Math.pow(2, level) * 5;
    }

    public void addRightAnswer() {
        rightAnswers++;
    }

    public void addWrongAnswer() {
        wrongAnswers++;
    }

    public void countPercentage() {
        setPercentage((double)rightAnswers / (rightAnswers + wrongAnswers) * 100);
    }
}