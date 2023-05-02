package com.sedlacek.quiz.entity;

import com.sedlacek.quiz.entity.EntityBase;
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
        this.exp = this.exp + exp;
    }

    public void countPercentage() {
        setPercentage((double)rightAnswers / (rightAnswers + wrongAnswers) * 100);
    }
}
