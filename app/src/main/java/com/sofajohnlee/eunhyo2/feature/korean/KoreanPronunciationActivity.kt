package com.sofajohnlee.eunhyo2.feature.korean

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sofajohnlee.eunhyo2.databinding.ActivityKoreanPronunciationBinding
import kotlinx.coroutines.launch

class KoreanPronunciationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKoreanPronunciationBinding
    private val viewModel: KoreanPronunciationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKoreanPronunciationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonPrevious.setOnClickListener { viewModel.previous() }
        binding.buttonNext.setOnClickListener { viewModel.next() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.textPosition.text = "${state.index + 1} / ${state.total}"
                    binding.textExample.text = state.current.example
                    binding.textCategory.text = state.current.category
                    binding.textGuide.text = state.guide
                }
            }
        }
    }
}
