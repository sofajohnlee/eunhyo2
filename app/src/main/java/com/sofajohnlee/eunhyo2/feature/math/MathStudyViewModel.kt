package com.sofajohnlee.eunhyo2.feature.math

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MathStudyViewModel : ViewModel() {
    data class UiState(
        val operation: MathOperation = MathOperation.ADD,
        val problem: MathProblem = MathProblemGenerator().generate(MathOperation.ADD),
        val input: String = "",
        val feedback: String = "",
    )

    private val generator = MathProblemGenerator()
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun selectOperation(operation: MathOperation) {
        _uiState.value = UiState(operation = operation, problem = generator.generate(operation))
    }

    fun updateInput(value: String) {
        _uiState.value = _uiState.value.copy(input = value.filter { it.isDigit() || it == '-' })
    }

    fun submit() {
        val answer = _uiState.value.input.toIntOrNull()
        val correct = answer == _uiState.value.problem.answer
        _uiState.value = _uiState.value.copy(feedback = if (correct) "정답입니다." else "다시 생각해 보세요.")
    }

    fun next() {
        val operation = _uiState.value.operation
        _uiState.value = UiState(operation = operation, problem = generator.generate(operation))
    }
}
