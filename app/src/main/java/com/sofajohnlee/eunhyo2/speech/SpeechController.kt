package com.sofajohnlee.eunhyo2.speech

import android.content.Context
import android.speech.tts.TextToSpeech
import com.sofajohnlee.eunhyo2.domain.model.StudyLanguage
import java.util.Locale

class SpeechController(context: Context) : TextToSpeech.OnInitListener {
    private val tts = TextToSpeech(context.applicationContext, this)
    private var ready = false

    override fun onInit(status: Int) {
        ready = status == TextToSpeech.SUCCESS
    }

    fun speak(text: String, language: StudyLanguage) {
        if (!ready || text.isBlank()) return
        tts.language = Locale.forLanguageTag(language.localeTag)
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "eunhyo2-learning-card")
    }

    fun shutdown() {
        tts.stop()
        tts.shutdown()
        ready = false
    }
}
