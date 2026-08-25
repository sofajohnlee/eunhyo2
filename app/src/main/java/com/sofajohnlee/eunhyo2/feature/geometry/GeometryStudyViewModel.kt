package com.sofajohnlee.eunhyo2.feature.geometry

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class GeometryUiState(
    val category: GeometryCategory = GeometryCategory.CIRCLE,
    val index: Int = 0,
) {
    val item: GeometryItem
        get() = GeometryCatalog.items(category)[index.coerceIn(0, GeometryCatalog.items(category).lastIndex)]
}

class GeometryStudyViewModel : ViewModel() {
    private val _state = MutableStateFlow(GeometryUiState())
    val state: StateFlow<GeometryUiState> = _state.asStateFlow()

    fun select(category: GeometryCategory) {
        _state.value = GeometryUiState(category = category)
    }

    fun next() {
        val current = _state.value
        val list = GeometryCatalog.items(current.category)
        _state.value = current.copy(index = (current.index + 1) % list.size)
    }

    fun previous() {
        val current = _state.value
        val list = GeometryCatalog.items(current.category)
        _state.value = current.copy(index = (current.index - 1 + list.size) % list.size)
    }
}
