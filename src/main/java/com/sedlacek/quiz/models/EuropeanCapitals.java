package com.sedlacek.quiz.models;

import java.util.HashMap;

public class EuropeanCapitals {
    private HashMap<String, String> capitals = new HashMap<>();

    public EuropeanCapitals(HashMap<String, String> capitals) {
        this.capitals = capitals;
        capitals.put("Česká republika", "Praha");
        capitals.put("Slovensko", "Bratislava");
        capitals.put("Rakousko", "Vídeň");
        capitals.put("Německo", "Berlín");
        capitals.put("Polsko", "Varšava");
        capitals.put("Francie", "Paříž");
        capitals.put("Itálie", "Řím");
        capitals.put("Velká Británie", "Londýn");
        capitals.put("Rusko", "Moskva");
    }

    public EuropeanCapitals() {

    }

    public HashMap<String, String> getCapitals() {
        return capitals;
    }

    public boolean rightAnswer(String state, String capital) {
        return capital.equals(this.capitals.get(state));
    }
}
