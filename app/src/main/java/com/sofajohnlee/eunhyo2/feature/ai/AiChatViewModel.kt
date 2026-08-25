package com.sofajohnlee.eunhyo2.feature.ai

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AiChatViewModel(
    private val engine: ChatEngine = LocalRuleChatEngine(),
) : ViewModel() {
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    fun send(text: String) {
        val trimmed = text.trim()
        if (trimmed.isEmpty()) return
        val current = _messages.value
        _messages.value = current + ChatMessage(trimmed, true) + ChatMessage(engine.respond(trimmed), false)
    }
}
