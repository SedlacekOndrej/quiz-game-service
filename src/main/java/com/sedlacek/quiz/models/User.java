package com.sedlacek.quiz.models;

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
    @OneToMany(mappedBy = "user")
    private List<LoginSession> loginSessions;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.level = 1;
        this.exp = 0L;
    }

    public void addExp(long exp) {
        this.exp = this.exp + exp;
    }
}
