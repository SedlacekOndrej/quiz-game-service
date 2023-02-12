package com.sedlacek.quiz.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class QuizWrongAnswer {
    private String state;

    private String selectedAnswer;

    private String correctAnswer;
}
