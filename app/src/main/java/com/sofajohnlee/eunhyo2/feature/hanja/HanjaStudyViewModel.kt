package com.sofajohnlee.eunhyo2.feature.hanja

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import java.io.InputStream
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HanjaStudyViewModel(application: Application) : AndroidViewModel(application) {
    data class UiState(
        val entries: List<HanjaEntry>,
        val index: Int = 0,
        val reveal: Boolean = false,
        val sourceLabel: String = "기본 데이터",
    ) {
        val current: HanjaEntry get() = entries[index]
    }

    private val repository = HanjaRepository()
    private val store = HanjaDataStore(application)
    private val saved = store.load()
    private val _uiState = MutableStateFlow(
        UiState(
            entries = saved.ifEmpty(repository::builtIn),
            sourceLabel = if (saved.isEmpty()) "기본 데이터" else "가져온 데이터 ${saved.size}자",
        ),
    )
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun next() {
        val state = _uiState.value
        val nextIndex = (state.index + 1) % state.entries.size
        _uiState.value = state.copy(index = nextIndex, reveal = false)
    }

    fun toggleReveal() {
        _uiState.value = _uiState.value.copy(reveal = !_uiState.value.reveal)
    }

    fun importCsv(input: InputStream): Int {
        val entries = repository.parseCsv(input)
        if (entries.isEmpty()) return 0
        store.save(entries)
        _uiState.value = UiState(entries = entries, sourceLabel = "가져온 데이터 ${entries.size}자")
        return entries.size
    }

    fun restoreBuiltIn() {
        store.clear()
        _uiState.value = UiState(entries = repository.builtIn(), sourceLabel = "기본 데이터")
    }
}
