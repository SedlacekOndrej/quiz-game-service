package com.sedlacek.quiz.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserResponse {
    private boolean isRegistered;

    private String responseMessage;
}
