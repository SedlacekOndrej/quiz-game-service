package com.sedlacek.quiz.services;

import com.sedlacek.quiz.models.States;
import com.sedlacek.quiz.repositories.UserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class CapitalServiceTests {
    CapitalService capitalService;

    @BeforeEach
    public void init() {
        var fakeUserService = Mockito.mock(UserService.class);

        var fakeUserRepository = Mockito.mock(UserRepository.class);

        capitalService = new CapitalService(fakeUserService, fakeUserRepository);
    }

    @Test
    public void Given_EuropeContinent_When_AnsweringAllQuestionsCorrectly_Then_ScoreIsCorrect() {
        var testStates = List.of(
                "Francie", "Kosovo", "Slovensko", "Řecko", "Polsko", "Bulharsko", "Itálie", "Švédsko", "Rusko", "Kypr"
        );

        var testAnswers = List.of(
                "Paříž", "Priština", "Bratislava", "Atény", "Varšava", "Sofie", "Řím", "Stockholm", "Moskva", "Nikósie"
        );

        capitalService.setStates(testStates);

        capitalService.setChosenContinent(States.Europe);

        capitalService.playTheQuiz(testAnswers);

        // The score is calculated in CapitalService.postAnswers() method.
        var actualScore = capitalService.getScore() * 10;

        Assertions.assertEquals(100, actualScore);
    }

    @Test
    public void Given_EuropeContinent_When_AnsweringSomeQuestionsWrongly_Then_FailedStatesContainCorrectStates_And_ScoreIsRight() {
        var testStates = List.of(
                "Francie", "Kosovo", "Slovensko", "Řecko", "Polsko", "Bulharsko", "Itálie", "Švédsko", "Rusko", "Kypr"
        );

        var testAnswers = List.of(
                "Paříž", "Praha", "Bratislava", "Bratislava", "Varšava", "Sofie", "Řím", "Oslo", "Moskva", "Nikósie"
        );

        capitalService.setStates(testStates);

        capitalService.setChosenContinent(States.Europe);

        capitalService.playTheQuiz(testAnswers);

        var actualScore = capitalService.getScore() * 10;

        Assertions.assertEquals(70, actualScore);

        var expectedFailedStates = List.of("Kosovo", "Řecko", "Švédsko");

        Assertions.assertArrayEquals(expectedFailedStates.toArray(), capitalService.getFailedStates().toArray());
    }
}