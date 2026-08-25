package com.sofajohnlee.eunhyo2.feature.koreanbook

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sofajohnlee.eunhyo2.databinding.ActivityKoreanBookBinding
import kotlinx.coroutines.launch

class KoreanBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKoreanBookBinding
    private val viewModel: KoreanBookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKoreanBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonPrevCharacter.setOnClickListener { viewModel.previousCharacter() }
        binding.buttonNextCharacter.setOnClickListener { viewModel.nextCharacter() }
        binding.buttonHappy.setOnClickListener { viewModel.setPlot(StoryPlot.HAPPY) }
        binding.buttonFunny.setOnClickListener { viewModel.setPlot(StoryPlot.FUNNY) }
        binding.buttonMoving.setOnClickListener { viewModel.setPlot(StoryPlot.MOVING) }
        binding.buttonKoreanLanguage.setOnClickListener { viewModel.setLanguage(StoryLanguage.KOREAN) }
        binding.buttonEnglishLanguage.setOnClickListener { viewModel.setLanguage(StoryLanguage.ENGLISH) }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.textBookPosition.text = "인물 ${state.settings.character}/13 · ${state.story.plot.label} · ${state.story.language.label}"
                    binding.textBookStory.text = state.story.text
                }
            }
        }
    }
}
