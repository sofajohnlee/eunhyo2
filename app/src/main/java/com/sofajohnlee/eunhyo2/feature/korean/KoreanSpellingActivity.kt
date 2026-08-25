package com.sofajohnlee.eunhyo2.feature.korean

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sofajohnlee.eunhyo2.databinding.ActivityKoreanSpellingBinding
import kotlinx.coroutines.launch

class KoreanSpellingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKoreanSpellingBinding
    private val viewModel: KoreanSpellingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKoreanSpellingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonPrev.setOnClickListener { viewModel.previous() }
        binding.buttonNext.setOnClickListener { viewModel.next() }
        binding.buttonReveal.setOnClickListener { viewModel.toggleAnswer() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::render)
            }
        }
    }

    private fun render(state: KoreanSpellingUiState) {
        binding.textPrompt.text = state.item.prompt
        binding.textAnswer.text = if (state.reveal) state.item.answer else ""
        binding.textExplanation.text = if (state.reveal) state.item.explanation else ""
        binding.buttonReveal.text = if (state.reveal) "정답 숨기기" else "정답 보기"
    }
}
