package com.sofajohnlee.eunhyo2.feature.math

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MathStudyViewModel : ViewModel() {
    data class UiState(
        val operation: MathOperation = MathOperation.ADD,
        val difficulty: MathDifficulty = MathDifficulty.INTERMEDIATE,
        val problem: MathProblem = MathProblemGenerator().generate(
            MathOperation.ADD,
            MathDifficulty.INTERMEDIATE,
        ),
        val input: String = "",
        val feedback: String = "",
        val correctCount: Int = 0,
        val attemptCount: Int = 0,
    )

    private val generator = MathProblemGenerator()
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun selectOperation(operation: MathOperation) {
        val state = _uiState.value
        _uiState.value = state.copy(
            operation = operation,
            problem = generator.generate(operation, state.difficulty),
            input = "",
            feedback = "",
        )
    }

    fun selectDifficulty(difficulty: MathDifficulty) {
        val state = _uiState.value
        _uiState.value = state.copy(
            difficulty = difficulty,
            problem = generator.generate(state.operation, difficulty),
            input = "",
            feedback = "",
        )
    }

    fun updateInput(value: String) {
        _uiState.value = _uiState.value.copy(input = value.filter { it.isDigit() || it == '-' })
    }

    fun submit() {
        val state = _uiState.value
        val answer = state.input.toIntOrNull()
        val correct = answer == state.problem.answer
        _uiState.value = state.copy(
            feedback = if (correct) "정답입니다." else "다시 생각해 보세요.",
            correctCount = state.correctCount + if (correct) 1 else 0,
            attemptCount = state.attemptCount + 1,
        )
    }

    fun next() {
        val state = _uiState.value
        _uiState.value = state.copy(
            problem = generator.generate(state.operation, state.difficulty),
            input = "",
            feedback = "",
        )
    }
}
