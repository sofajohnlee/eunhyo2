package com.sofajohnlee.eunhyo2.feature.korean

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sofajohnlee.eunhyo2.databinding.ActivityKoreanIdiomBinding
import kotlinx.coroutines.launch

class KoreanIdiomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKoreanIdiomBinding
    private val viewModel: KoreanIdiomViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKoreanIdiomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonPrevious.setOnClickListener { viewModel.previous() }
        binding.buttonMeaning.setOnClickListener { viewModel.toggleMeaning() }
        binding.buttonNext.setOnClickListener { viewModel.next() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.textPhrase.text = state.item.phrase
                    binding.textMeaning.text = if (state.showMeaning) state.item.meaning else "뜻 보기 버튼을 눌러 확인하세요."
                    binding.textPosition.text = "${state.index + 1} / ${KoreanIdiomRepository.items.size}"
                }
            }
        }
    }
}
