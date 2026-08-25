package com.sofajohnlee.eunhyo2.feature.korean

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class KoreanSpellingUiState(
    val index: Int = 0,
    val reveal: Boolean = false,
) {
    val item: KoreanSpellingRule get() = KoreanSpellingRepository.rules[index]
}

class KoreanSpellingViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(KoreanSpellingUiState())
    val uiState: StateFlow<KoreanSpellingUiState> = _uiState.asStateFlow()

    fun next() = move(1)
    fun previous() = move(-1)
    fun toggleAnswer() { _uiState.value = _uiState.value.copy(reveal = !_uiState.value.reveal) }

    private fun move(delta: Int) {
        val size = KoreanSpellingRepository.rules.size
        val next = (_uiState.value.index + delta + size) % size
        _uiState.value = KoreanSpellingUiState(index = next)
    }
}
