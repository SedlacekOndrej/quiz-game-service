package com.sedlacek.quiz.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class QuizPlayResult {
    private User user;

    private long score;

    private List<QuizWrongAnswer> wrongAnswers;
}
