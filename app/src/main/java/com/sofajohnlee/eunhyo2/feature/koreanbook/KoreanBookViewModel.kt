package com.sofajohnlee.eunhyo2.feature.koreanbook

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class KoreanBookUiState(
    val settings: KoreanBookSettings,
    val story: KoreanBookStory,
)

class KoreanBookViewModel(application: Application) : AndroidViewModel(application) {
    private val settingsRepository = KoreanBookSettingsRepository(application)
    private val storyRepository = KoreanBookStoryRepository()

    private val _uiState = MutableStateFlow(createState(settingsRepository.load()))
    val uiState: StateFlow<KoreanBookUiState> = _uiState.asStateFlow()

    fun nextCharacter() = update { it.copy(character = if (it.character >= 13) 1 else it.character + 1) }
    fun previousCharacter() = update { it.copy(character = if (it.character <= 1) 13 else it.character - 1) }
    fun setPlot(plot: StoryPlot) = update { it.copy(plot = plot.legacyValue) }
    fun setLanguage(language: StoryLanguage) = update { it.copy(language = language.legacyValue) }

    private fun update(transform: (KoreanBookSettings) -> KoreanBookSettings) {
        val updated = transform(_uiState.value.settings)
        settingsRepository.save(updated)
        _uiState.value = createState(updated)
    }

    private fun createState(settings: KoreanBookSettings): KoreanBookUiState =
        KoreanBookUiState(settings, storyRepository.story(settings))
}
