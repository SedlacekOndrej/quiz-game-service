package com.sedlacek.quiz.model;

import com.sedlacek.quiz.entity.EntityBase;
import com.sedlacek.quiz.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Sessions")
public class LoginSession extends EntityBase {
    private LocalDateTime created;
    @ManyToOne
    private User user;

    public LoginSession(User user) {
        this.user = user;
        this.created = LocalDateTime.now();
    }

    public User tryGetLoggedUser() {
        if (user != null) {
            return user;
        }
        return null;
    }
}
