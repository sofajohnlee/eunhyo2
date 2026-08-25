package com.sofajohnlee.eunhyo2.feature.math

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MathStudyViewModel : ViewModel() {
    data class UiState(
        val operation: MathOperation = MathOperation.ADD,
        val difficulty: MathDifficulty = MathDifficulty.INTERMEDIATE,
        val numberMode: NumberMode = NumberMode.NATURAL,
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
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun selectOperation(operation: MathOperation) = regenerate(operation = operation)

    fun selectDifficulty(difficulty: MathDifficulty) = regenerate(difficulty = difficulty)

    fun selectNumberMode(numberMode: NumberMode) = regenerate(numberMode = numberMode)

    fun updateInput(value: String) {
        _uiState.value = _uiState.value.copy(
            input = value.filter { it.isDigit() || it == '-' || it == '.' || it == '/' },
        )
    }

    fun submit() {
        val state = _uiState.value
        if (state.input.isBlank()) return
        val correct = state.exercise.matches(state.input)
        _uiState.value = state.copy(
            feedback = if (correct) "정답입니다." else "다시 생각해 보세요.",
            correctCount = state.correctCount + if (correct) 1 else 0,
            attemptCount = state.attemptCount + 1,
        )
    }

    fun next() = regenerate()

    private fun regenerate(
        operation: MathOperation = _uiState.value.operation,
        difficulty: MathDifficulty = _uiState.value.difficulty,
        numberMode: NumberMode = _uiState.value.numberMode,
    ) {
        val state = _uiState.value
        _uiState.value = state.copy(
            operation = operation,
            difficulty = difficulty,
            numberMode = numberMode,
            exercise = generator.generate(numberMode, operation, difficulty),
            input = "",
            feedback = "",
        )
    }
}
