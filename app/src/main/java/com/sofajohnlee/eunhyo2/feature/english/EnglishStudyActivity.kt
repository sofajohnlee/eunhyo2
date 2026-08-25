package com.sofajohnlee.eunhyo2.feature.english

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sofajohnlee.eunhyo2.databinding.ActivityEnglishStudyBinding
import com.sofajohnlee.eunhyo2.domain.model.StudyLanguage
import com.sofajohnlee.eunhyo2.speech.SpeechController
import kotlinx.coroutines.launch

class EnglishStudyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnglishStudyBinding
    private val viewModel: EnglishStudyViewModel by viewModels()
    private lateinit var speechController: SpeechController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnglishStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        speechController = SpeechController(this)

        binding.buttonEnglish.setOnClickListener { viewModel.selectLanguage(StudyLanguage.ENGLISH) }
        binding.buttonFrench.setOnClickListener { viewModel.selectLanguage(StudyLanguage.FRENCH) }
        binding.buttonChinese.setOnClickListener { viewModel.selectLanguage(StudyLanguage.CHINESE) }
        binding.buttonJapanese.setOnClickListener { viewModel.selectLanguage(StudyLanguage.JAPANESE) }
        binding.buttonItalian.setOnClickListener { viewModel.selectLanguage(StudyLanguage.ITALIAN) }
        binding.buttonNext.setOnClickListener { viewModel.next() }
        binding.buttonSpeak.setOnClickListener {
            val state = viewModel.uiState.value
            speechController.speak(state.card.speechText, state.language)
        }
        binding.checkShowImage.setOnCheckedChangeListener { _, checked -> viewModel.setShowImage(checked) }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.textSymbol.text = state.card.primaryText
                    binding.textTerm.text = state.card.secondaryText
                    binding.textHint.text = state.card.speechText
                    binding.textImageStatus.text = if (state.showImage) state.card.imageResourceName.orEmpty() else ""
                }
            }
        }
    }

    override fun onDestroy() {
        speechController.shutdown()
        super.onDestroy()
    }
}
