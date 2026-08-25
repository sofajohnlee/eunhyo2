package com.sofajohnlee.eunhyo2.feature.math

enum class MathDifficulty(
    val maxOperand: Int,
    val label: String,
) {
    BEGINNER(10, "초급"),
    INTERMEDIATE(100, "중급"),
    ADVANCED(1000, "고급"),
}
