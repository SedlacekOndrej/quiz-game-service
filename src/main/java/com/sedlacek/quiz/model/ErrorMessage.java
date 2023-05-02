package com.sedlacek.quiz.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorMessage {
    private String message;
    public static boolean isError;

    public ErrorMessage(String message) {
        this.message = message;
    }
}
