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
        speak(text, language.localeTag)
    }

    fun speak(text: String, localeTag: String) {
        if (!ready || text.isBlank()) return
        tts.language = Locale.forLanguageTag(localeTag)
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "eunhyo2-speech")
    }

    fun stop() {
        if (ready) tts.stop()
    }

    fun shutdown() {
        tts.stop()
        tts.shutdown()
        ready = false
    }
}
