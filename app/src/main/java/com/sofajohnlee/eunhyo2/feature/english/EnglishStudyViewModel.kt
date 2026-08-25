package com.sofajohnlee.eunhyo2.feature.english

import androidx.lifecycle.ViewModel
import com.sofajohnlee.eunhyo2.data.EnglishAlphabetRepository
import com.sofajohnlee.eunhyo2.domain.model.LearningCard
import com.sofajohnlee.eunhyo2.domain.model.StudyLanguage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EnglishStudyViewModel : ViewModel() {
    private val repository = EnglishAlphabetRepository()

    data class UiState(
        val language: StudyLanguage = StudyLanguage.ENGLISH,
        val index: Int = 0,
        val showImage: Boolean = true,
        val card: LearningCard,
    )

    private val _uiState = MutableStateFlow(
        UiState(card = repository.cards(StudyLanguage.ENGLISH).first())
    )
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun selectLanguage(language: StudyLanguage) {
        val cards = repository.cards(language)
        if (cards.isEmpty()) return
        _uiState.value = _uiState.value.copy(language = language, index = 0, card = cards.first())
    }

    fun next() {
        val state = _uiState.value
        val cards = repository.cards(state.language)
        if (cards.isEmpty()) return
        val nextIndex = (state.index + 1) % cards.size
        _uiState.value = state.copy(index = nextIndex, card = cards[nextIndex])
    }

    fun setShowImage(show: Boolean) {
        _uiState.value = _uiState.value.copy(showImage = show)
    }
}
