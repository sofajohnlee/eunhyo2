package com.sofajohnlee.eunhyo2.feature.personality

data class PersonalityAnswers(
    val q1: Boolean,
    val q2: Boolean,
    val q3: Boolean,
    val q4: Boolean,
)

object PersonalityQuiz {
    fun result(answers: PersonalityAnswers): String {
        if (!answers.q1) return "내향형 성향"
        return when {
            answers.q2 && answers.q3 && answers.q4 -> "외향형 성향"
            answers.q2 && answers.q3 && !answers.q4 -> "직관형 성향"
            answers.q2 && !answers.q3 && answers.q4 -> "사고형 성향"
            answers.q2 && !answers.q3 && !answers.q4 -> "인식형 성향"
            !answers.q2 && answers.q3 && answers.q4 -> "감각형 성향"
            !answers.q2 && answers.q3 && !answers.q4 -> "판단형 성향"
            !answers.q2 && !answers.q3 && answers.q4 -> "감성형 성향"
            else -> "내향형 성향"
        }
    }
}
