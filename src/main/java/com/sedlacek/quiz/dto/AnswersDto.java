package com.sedlacek.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswersDto {
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String answer5;
    private String answer6;
    private String answer7;
    private String answer8;
    private String answer9;
    private String answer10;

    public List<String> answeredCapitals() {
        List<String> capitals = new ArrayList<>();
        capitals.add(this.answer1);
        capitals.add(this.answer2);
        capitals.add(this.answer3);
        capitals.add(this.answer4);
        capitals.add(this.answer5);
        capitals.add(this.answer6);
        capitals.add(this.answer7);
        capitals.add(this.answer8);
        capitals.add(this.answer9);
        capitals.add(this.answer10);
        return capitals;
    }
}
