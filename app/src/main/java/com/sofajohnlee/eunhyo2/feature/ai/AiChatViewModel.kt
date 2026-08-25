package com.sofajohnlee.eunhyo2.feature.ai

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AiChatViewModel(application: Application) : AndroidViewModel(application) {
    private val engine: ChatEngine = AssetAimlChatEngine(application)

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    fun send(text: String) {
        val trimmed = text.trim()
        if (trimmed.isEmpty()) return
        val current = _messages.value
        _messages.value = current + ChatMessage(trimmed, true) + ChatMessage(engine.respond(trimmed), false)
    }
}
