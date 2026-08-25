package com.sofajohnlee.eunhyo2

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class GradeLevel(val label: String) {
    ELEMENTARY("초등학교"),
    MIDDLE("중학교"),
    HIGH("고등학교")
}

data class MainUiState(val grade: GradeLevel = GradeLevel.ELEMENTARY)

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    fun selectGrade(grade: GradeLevel) {
        _uiState.value = _uiState.value.copy(grade = grade)
    }
}
