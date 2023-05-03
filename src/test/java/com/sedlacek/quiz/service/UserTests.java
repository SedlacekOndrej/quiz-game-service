package com.sedlacek.quiz.service;


import com.sedlacek.quiz.entity.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class UserTests {
    @ParameterizedTest
    @CsvSource(
            {"1, 0, 10, 2", "1, 0, 70, 4", "4, 50, 30, 5", "4, 50, 29, 4", "1, 0, 5, 1", "1, 0, 2621440, 20"}
    )
    public void given_userWithLevel_When_AddingExperienceToUser_Then_LevelUpIsCorrectlyIncreased(int level, long experience, long experienceGained, long expectedLevelResult) {
        var user = new User();

        user.setLevel(level);

        user.setExp(experience);

        user.addExp(experienceGained);

        Assertions.assertEquals(expectedLevelResult, user.getLevel());
    }
}
