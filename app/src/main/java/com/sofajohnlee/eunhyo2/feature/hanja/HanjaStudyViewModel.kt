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
    private val legacyEmbedded = if (saved.isEmpty()) loadLegacyEmbedded(application) else emptyList()
    private val initialEntries = when {
        saved.isNotEmpty() -> saved
        legacyEmbedded.isNotEmpty() -> legacyEmbedded
        else -> repository.builtIn()
    }
    private val initialLabel = when {
        saved.isNotEmpty() -> "가져온 데이터 ${saved.size}자"
        legacyEmbedded.isNotEmpty() -> "원본 내장 한자 ${legacyEmbedded.size}자"
        else -> "기본 데이터"
    }

    private val _uiState = MutableStateFlow(
        UiState(entries = initialEntries, sourceLabel = initialLabel),
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
        val application = getApplication<Application>()
        val legacy = loadLegacyEmbedded(application)
        _uiState.value = if (legacy.isNotEmpty()) {
            UiState(entries = legacy, sourceLabel = "원본 내장 한자 ${legacy.size}자")
        } else {
            UiState(entries = repository.builtIn(), sourceLabel = "기본 데이터")
        }
    }

    private fun loadLegacyEmbedded(application: Application): List<HanjaEntry> = runCatching {
        application.assets.open(LEGACY_ASSET_PATH).use(repository::parseCsv)
    }.getOrDefault(emptyList())

    companion object {
        private const val LEGACY_ASSET_PATH = "hanja/legacy_embedded.csv"
    }
}
