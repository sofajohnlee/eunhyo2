package com.sofajohnlee.eunhyo2.feature.math

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MathStudyViewModel(application: Application) : AndroidViewModel(application) {
    enum class PracticeMode { STANDARD, MIXED }

    data class UiState(
        val operation: MathOperation = MathOperation.ADD,
        val difficulty: MathDifficulty = MathDifficulty.INTERMEDIATE,
        val numberMode: NumberMode = NumberMode.NATURAL,
        val practiceMode: PracticeMode = PracticeMode.STANDARD,
        val exercise: MathExercise = AdvancedMathProblemGenerator().generate(
            NumberMode.NATURAL,
            MathOperation.ADD,
            MathDifficulty.INTERMEDIATE,
        ),
        val input: String = "",
        val feedback: String = "",
        val correctCount: Int = 0,
        val attemptCount: Int = 0,
    ) {
        val scoreLabel: String get() = "$correctCount / $attemptCount"
    }

    private val generator = AdvancedMathProblemGenerator()
    private val mixedGenerator = MixedMathGenerator()
    private val progressRepository = MathProgressRepository(application)
    private val savedProgress = progressRepository.load()

    private val _uiState = MutableStateFlow(
        UiState(
            correctCount = savedProgress.correct,
            attemptCount = savedProgress.attempts,
        ),
    )
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun selectOperation(operation: MathOperation) = regenerate(operation = operation, practiceMode = PracticeMode.STANDARD)

    fun selectDifficulty(difficulty: MathDifficulty) = regenerate(difficulty = difficulty)

    fun selectNumberMode(numberMode: NumberMode) = regenerate(numberMode = numberMode, practiceMode = PracticeMode.STANDARD)

    fun selectMixedMode() = regenerate(practiceMode = PracticeMode.MIXED)

    fun updateInput(value: String) {
        _uiState.value = _uiState.value.copy(
            input = value.filter { it.isDigit() || it == '-' || it == '.' || it == '/' },
        )
    }

    fun submit() {
        val state = _uiState.value
        if (state.input.isBlank()) return
        val correct = state.exercise.matches(state.input)
        val updated = state.copy(
            feedback = if (correct) "정답입니다." else "다시 생각해 보세요.",
            correctCount = state.correctCount + if (correct) 1 else 0,
            attemptCount = state.attemptCount + 1,
        )
        _uiState.value = updated
        progressRepository.save(MathProgress(updated.correctCount, updated.attemptCount))
    }

    fun resetProgress() {
        progressRepository.reset()
        _uiState.value = _uiState.value.copy(correctCount = 0, attemptCount = 0, feedback = "점수를 초기화했습니다.")
    }

    fun next() = regenerate()

    private fun regenerate(
        operation: MathOperation = _uiState.value.operation,
        difficulty: MathDifficulty = _uiState.value.difficulty,
        numberMode: NumberMode = _uiState.value.numberMode,
        practiceMode: PracticeMode = _uiState.value.practiceMode,
    ) {
        val state = _uiState.value
        val exercise: MathExercise = when (practiceMode) {
            PracticeMode.STANDARD -> generator.generate(numberMode, operation, difficulty)
            PracticeMode.MIXED -> mixedGenerator.generate(difficulty).exercise
        }
        _uiState.value = state.copy(
            operation = operation,
            difficulty = difficulty,
            numberMode = numberMode,
            practiceMode = practiceMode,
            exercise = exercise,
            input = "",
            feedback = "",
        )
    }
}
