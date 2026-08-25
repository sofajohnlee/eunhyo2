package com.sofajohnlee.eunhyo2.feature.korean

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class KoreanPhraseViewModel(
    private val repository: KoreanPhraseRepository = KoreanPhraseRepository(),
) : ViewModel() {
    private val _index = MutableStateFlow(0)
    val index: StateFlow<Int> = _index.asStateFlow()

    val currentPhrase: String
        get() = repository.phrases[_index.value]

    fun next() {
        _index.value = (_index.value + 1) % repository.phrases.size
    }

    fun previous() {
        _index.value = (_index.value - 1 + repository.phrases.size) % repository.phrases.size
    }
}
