package com.sedlacek.quiz.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class RegisterUserDto {
private String username;

private String email;

private String password;

private String passwordConfirm;
}

