package com.sofajohnlee.eunhyo2.feature.korean

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sofajohnlee.eunhyo2.databinding.ActivityKoreanStudyBinding
import com.sofajohnlee.eunhyo2.domain.model.StudyLanguage
import com.sofajohnlee.eunhyo2.speech.SpeechController
import kotlinx.coroutines.launch

class KoreanStudyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKoreanStudyBinding
    private val viewModel: KoreanStudyViewModel by viewModels()
    private lateinit var speechController: SpeechController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKoreanStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        speechController = SpeechController(this)

        binding.buttonPrevious.setOnClickListener { viewModel.previous() }
        binding.buttonNext.setOnClickListener { viewModel.next() }
        binding.buttonSpeak.setOnClickListener {
            speechController.speak(viewModel.uiState.value.card.speechText, StudyLanguage.KOREAN)
        }
        binding.checkShowImage.setOnCheckedChangeListener { _, checked -> viewModel.setShowImage(checked) }
        binding.checkDrawingGuide.setOnCheckedChangeListener { _, checked -> viewModel.setShowDrawingGuide(checked) }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.textSymbol.text = state.card.primaryText
                    binding.textWord.text = state.card.secondaryText
                    binding.textImageStatus.text = if (state.showImage) {
                        state.card.imageResourceName ?: getString(com.sofajohnlee.eunhyo2.R.string.image_pending)
                    } else {
                        ""
                    }
                    binding.textDrawingStatus.text = if (state.showDrawingGuide) {
                        getString(com.sofajohnlee.eunhyo2.R.string.drawing_guide_enabled)
                    } else {
                        getString(com.sofajohnlee.eunhyo2.R.string.drawing_guide_disabled)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        speechController.shutdown()
        super.onDestroy()
    }
}
