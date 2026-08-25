package com.sofajohnlee.eunhyo2.feature.koreanbook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.sofajohnlee.eunhyo2.databinding.ActivityKoreanBookEditorBinding
import com.sofajohnlee.eunhyo2.speech.SpeechController

class KoreanBookEditorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKoreanBookEditorBinding
    private lateinit var speechController: SpeechController
    private lateinit var settingsRepository: KoreanBookSettingsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKoreanBookEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        speechController = SpeechController(this)
        settingsRepository = KoreanBookSettingsRepository(this)

        val initialText = intent.getStringExtra(EXTRA_TEXT).orEmpty()
        binding.editStory.setText(initialText)
        binding.textPreview.text = initialText

        binding.editStory.doAfterTextChanged { binding.textPreview.text = it?.toString().orEmpty() }
        binding.buttonRead.setOnClickListener {
            val languageTag = if (settingsRepository.load().language == 1) "ko-KR" else "en-US"
            speechController.speak(binding.textPreview.text.toString(), languageTag)
        }
        binding.buttonStop.setOnClickListener { speechController.stop() }
        binding.buttonClose.setOnClickListener { finish() }
    }

    override fun onDestroy() {
        speechController.shutdown()
        super.onDestroy()
    }

    companion object {
        const val EXTRA_TEXT = "book_text"
    }
}
