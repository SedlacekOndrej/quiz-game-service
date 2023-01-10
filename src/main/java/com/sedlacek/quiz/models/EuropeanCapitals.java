package com.sedlacek.quiz.models;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class EuropeanCapitals {
    private final HashMap<String, String> capitals;

    public EuropeanCapitals(HashMap<String, String> capitals) {
        this.capitals = capitals;
        capitals.put("Albánie", "Tirana");
        capitals.put("Andorra", "Andorra la Vella");
        capitals.put("Arménie", "Jerevan");
        capitals.put("Ázerbájdžán", "Baku");
        capitals.put("Belgie", "Brusel");
        capitals.put("Bělorusko", "Minsk");
        capitals.put("Bosna a Hercegovina", "Sarajevo");
        capitals.put("Bulharsko", "Sofie");
        capitals.put("Černá Hora", "Podgorica");
        capitals.put("Česká republika", "Praha");
        capitals.put("Dánsko", "Kodaň");
        capitals.put("Estonsko", "Tallinn");
        capitals.put("Finsko", "Helsinki");
        capitals.put("Francie", "Paříž");
        capitals.put("Gruzie", "Tbilisi");
        capitals.put("Chorvatsko", "Záhřeb");
        capitals.put("Irsko", "Dublin");
        capitals.put("Island", "Reykjavík");
        capitals.put("Itálie", "Řím");
        capitals.put("Kazachstán", "Astana");
        capitals.put("Kosovo", "Priština");
        capitals.put("Kypr", "Nikósie");
        capitals.put("Lichtenštejnsko", "Vaduz");
        capitals.put("Litva", "Vilnius");
        capitals.put("Lotyšsko", "Riga");
        capitals.put("Lucembursko", "Lucemburk");
        capitals.put("Maďarsko", "Budapešť");
        capitals.put("Malta", "Valletta");
        capitals.put("Moldavsko", "Kišiněv");
        capitals.put("Monako", "Monaco-Ville");
        capitals.put("Německo", "Berlín");
        capitals.put("Nizozemsko", "Amsterdam");
        capitals.put("Norsko", "Oslo");
        capitals.put("Polsko", "Varšava");
        capitals.put("Portugalsko", "Lisabon");
        capitals.put("Rakousko", "Vídeň");
        capitals.put("Rumunsko", "Bukurešť");
        capitals.put("Rusko", "Moskva");
        capitals.put("Řecko", "Atény");
        capitals.put("San Marino", "San Marino");
        capitals.put("Severní Makedonie", "Skopje");
        capitals.put("Slovensko", "Bratislava");
        capitals.put("Slovinsko", "Lublaň");
        capitals.put("Spojené království", "Londýn");
        capitals.put("Srbsko", "Bělehrad");
        capitals.put("Španělsko", "Madrid");
        capitals.put("Švédsko", "Stockholm");
        capitals.put("Švýcarsko", "Bern");
        capitals.put("Turecko", "Ankara");
        capitals.put("Ukrajina", "Kyjev");
        capitals.put("Vatikán", "Vatikán");
    }
}
