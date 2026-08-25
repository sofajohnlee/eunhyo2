package com.sofajohnlee.eunhyo2.feature.math

import android.content.Context

data class MathProgress(
    val correct: Int = 0,
    val attempts: Int = 0,
) {
    val accuracyPercent: Int
        get() = if (attempts == 0) 0 else (correct * 100 / attempts)
}

class MathProgressRepository(context: Context) {
    private val preferences = context.applicationContext
        .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun load(): MathProgress = MathProgress(
        correct = preferences.getInt(KEY_CORRECT, 0),
        attempts = preferences.getInt(KEY_ATTEMPTS, 0),
    )

    fun save(progress: MathProgress) {
        preferences.edit()
            .putInt(KEY_CORRECT, progress.correct.coerceAtLeast(0))
            .putInt(KEY_ATTEMPTS, progress.attempts.coerceAtLeast(0))
            .apply()
    }

    fun reset() = save(MathProgress())

    companion object {
        private const val PREF_NAME = "math_progress"
        private const val KEY_CORRECT = "correct"
        private const val KEY_ATTEMPTS = "attempts"
    }
}
