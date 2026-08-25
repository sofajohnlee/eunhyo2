package com.sofajohnlee.eunhyo2.feature.hanja

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HanjaStudyViewModel : ViewModel() {
    data class UiState(
        val entries: List<HanjaEntry>,
        val index: Int = 0,
        val reveal: Boolean = false,
    ) {
        val current: HanjaEntry get() = entries[index]
    }

    private val repository = HanjaRepository()
    private val _uiState = MutableStateFlow(UiState(repository.builtIn()))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun next() {
        val state = _uiState.value
        val nextIndex = (state.index + 1) % state.entries.size
        _uiState.value = state.copy(index = nextIndex, reveal = false)
    }

    fun toggleReveal() {
        _uiState.value = _uiState.value.copy(reveal = !_uiState.value.reveal)
    }
}
