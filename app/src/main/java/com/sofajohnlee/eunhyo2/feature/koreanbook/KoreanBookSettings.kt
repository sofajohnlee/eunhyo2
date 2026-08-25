package com.sofajohnlee.eunhyo2.feature.koreanbook

data class KoreanBookSettings(
    val character: Int = 1,
    val plot: Int = 1,
    val language: Int = 1,
)

enum class StoryPlot(val legacyValue: Int, val label: String) {
    HAPPY(1, "행복"),
    FUNNY(2, "웃음"),
    MOVING(3, "감동"),
}

enum class StoryLanguage(val legacyValue: Int, val label: String) {
    KOREAN(1, "한국어"),
    ENGLISH(2, "English"),
}
