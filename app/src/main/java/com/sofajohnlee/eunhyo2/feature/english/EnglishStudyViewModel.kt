package com.sofajohnlee.eunhyo2.feature.english

import androidx.lifecycle.ViewModel
import com.sofajohnlee.eunhyo2.core.model.LearningCard
import com.sofajohnlee.eunhyo2.core.model.StudyLanguage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EnglishStudyViewModel : ViewModel() {
    data class UiState(
        val language: StudyLanguage = StudyLanguage.ENGLISH,
        val index: Int = 0,
        val showImage: Boolean = true,
        val card: LearningCard = EnglishAlphabetRepository.cards(StudyLanguage.ENGLISH).first()
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun selectLanguage(language: StudyLanguage) {
        val cards = EnglishAlphabetRepository.cards(language)
        _uiState.value = _uiState.value.copy(language = language, index = 0, card = cards.first())
    }

    fun next() {
        val state = _uiState.value
        val cards = EnglishAlphabetRepository.cards(state.language)
        val nextIndex = (state.index + 1) % cards.size
        _uiState.value = state.copy(index = nextIndex, card = cards[nextIndex])
    }

    fun setShowImage(show: Boolean) {
        _uiState.value = _uiState.value.copy(showImage = show)
    }
}
