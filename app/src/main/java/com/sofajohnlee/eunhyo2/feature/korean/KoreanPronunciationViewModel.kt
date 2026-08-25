package com.sofajohnlee.eunhyo2.feature.korean

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class KoreanPronunciationUiState(
    val index: Int = 0,
    val current: KoreanPronunciationCase,
    val total: Int,
    val guide: String,
)

class KoreanPronunciationViewModel : ViewModel() {
    private val repository = KoreanPronunciationRepository()
    private val items = repository.cases
    private val _uiState = MutableStateFlow(stateFor(0))
    val uiState: StateFlow<KoreanPronunciationUiState> = _uiState.asStateFlow()

    fun next() = _uiState.update { stateFor((it.index + 1) % items.size) }
    fun previous() = _uiState.update { stateFor((it.index - 1 + items.size) % items.size) }

    private fun stateFor(index: Int) = KoreanPronunciationUiState(
        index = index,
        current = items[index],
        total = items.size,
        guide = repository.finalConsonantGuide,
    )
}
