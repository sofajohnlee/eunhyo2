package com.sofajohnlee.eunhyo2.feature.hanja

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sofajohnlee.eunhyo2.databinding.ActivityHanjaStudyBinding
import kotlinx.coroutines.launch

class HanjaStudyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHanjaStudyBinding
    private val viewModel: HanjaStudyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHanjaStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonReveal.setOnClickListener { viewModel.toggleReveal() }
        binding.buttonNext.setOnClickListener { viewModel.next() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.textHanja.text = state.current.character
                    binding.textReading.text = if (state.reveal) state.current.reading else ""
                    binding.textMeaning.text = if (state.reveal) state.current.meaning else ""
                }
            }
        }
    }
}
