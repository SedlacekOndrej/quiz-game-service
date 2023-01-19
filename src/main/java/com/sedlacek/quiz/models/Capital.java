package com.sedlacek.quiz.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Capital {
    private String capital1;
    private String capital2;
    private String capital3;
    private String capital4;
    private String capital5;
    private String capital6;
    private String capital7;
    private String capital8;
    private String capital9;
    private String capital10;

    public Capital(String capital1, String capital2, String capital3, String capital4, String capital5,
                   String capital6, String capital7, String capital8, String capital9, String capital10) {
        this.capital1 = capital1;
        this.capital2 = capital2;
        this.capital3 = capital3;
        this.capital4 = capital4;
        this.capital5 = capital5;
        this.capital6 = capital6;
        this.capital7 = capital7;
        this.capital8 = capital8;
        this.capital9 = capital9;
        this.capital10 = capital10;
    }

    public List<String> answeredCapitals() {
        List<String> capitals = new ArrayList<>();
        capitals.add(this.capital1);
        capitals.add(this.capital2);
        capitals.add(this.capital3);
        capitals.add(this.capital4);
        capitals.add(this.capital5);
        capitals.add(this.capital6);
        capitals.add(this.capital7);
        capitals.add(this.capital8);
        capitals.add(this.capital9);
        capitals.add(this.capital10);
        return capitals;
    }
}
