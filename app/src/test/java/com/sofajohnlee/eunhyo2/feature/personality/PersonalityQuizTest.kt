package com.sofajohnlee.eunhyo2.feature.personality

import org.junit.Assert.assertEquals
import org.junit.Test

class PersonalityQuizTest {
    @Test
    fun allYesMapsToExtroverted() {
        assertEquals(
            "외향형 성향",
            PersonalityQuiz.result(PersonalityAnswers(true, true, true, true)),
        )
    }

    @Test
    fun firstNoMapsToIntroverted() {
        assertEquals(
            "내향형 성향",
            PersonalityQuiz.result(PersonalityAnswers(false, true, true, true)),
        )
    }

    @Test
    fun mixedAnswersKeepLegacyDecisionTree() {
        assertEquals(
            "판단형 성향",
            PersonalityQuiz.result(PersonalityAnswers(true, false, true, false)),
        )
    }
}
