package com.sofajohnlee.eunhyo2.feature.math

/** UI-neutral exercise representation supporting natural, decimal and fraction modes. */
data class MathExercise(
    val expression: String,
    val expectedAnswer: String,
) {
    fun matches(raw: String): Boolean = normalize(raw) == normalize(expectedAnswer)

    private fun normalize(value: String): String {
        val text = value.trim()
        if ('/' in text) {
            val parts = text.split('/')
            if (parts.size == 2) {
                val n = parts[0].trim().toIntOrNull()
                val d = parts[1].trim().toIntOrNull()
                if (n != null && d != null && d != 0) return Fraction.of(n, d).toString()
            }
        }
        return text.toBigDecimalOrNull()?.stripTrailingZeros()?.toPlainString() ?: text
    }
}
