package com.sofajohnlee.eunhyo2.feature.korean

import androidx.lifecycle.ViewModel
import com.sofajohnlee.eunhyo2.domain.model.LearningCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class KoreanStudyViewModel : ViewModel() {
    data class UiState(
        val index: Int = 0,
        val showImage: Boolean = true,
        val showDrawingGuide: Boolean = true,
        val card: LearningCard = KoreanLearningRepository.cards.first(),
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun next() {
        val next = (_uiState.value.index + 1) % KoreanLearningRepository.cards.size
        _uiState.value = _uiState.value.copy(index = next, card = KoreanLearningRepository.cards[next])
    }

    fun previous() {
        val current = _uiState.value.index
        val previous = if (current == 0) KoreanLearningRepository.cards.lastIndex else current - 1
        _uiState.value = _uiState.value.copy(index = previous, card = KoreanLearningRepository.cards[previous])
    }

    fun setShowImage(show: Boolean) {
        _uiState.value = _uiState.value.copy(showImage = show)
    }

    fun setShowDrawingGuide(show: Boolean) {
        _uiState.value = _uiState.value.copy(showDrawingGuide = show)
    }
}
