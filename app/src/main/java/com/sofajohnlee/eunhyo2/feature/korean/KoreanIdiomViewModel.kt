package com.sofajohnlee.eunhyo2.feature.korean

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class KoreanIdiomViewModel : ViewModel() {
    private val items = KoreanIdiomRepository.items
    private val _state = MutableStateFlow(IdiomState(items.first(), 0, false))
    val state = _state.asStateFlow()

    fun next() = move(1)
    fun previous() = move(-1)
    fun toggleMeaning() { _state.value = _state.value.copy(showMeaning = !_state.value.showMeaning) }

    private fun move(delta: Int) {
        val nextIndex = (_state.value.index + delta).mod(items.size)
        _state.value = IdiomState(items[nextIndex], nextIndex, false)
    }
}

data class IdiomState(val item: KoreanIdiom, val index: Int, val showMeaning: Boolean)
