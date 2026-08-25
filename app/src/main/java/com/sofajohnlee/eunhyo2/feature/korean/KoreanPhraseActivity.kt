package com.sofajohnlee.eunhyo2.feature.korean

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sofajohnlee.eunhyo2.databinding.ActivityKoreanPhraseBinding
import kotlinx.coroutines.launch
import kotlin.random.Random

class KoreanPhraseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKoreanPhraseBinding
    private val viewModel: KoreanPhraseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKoreanPhraseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonPrevious.setOnClickListener { viewModel.previous() }
        binding.buttonNext.setOnClickListener { viewModel.next() }
        binding.textPhrase.setOnClickListener {
            binding.textPhrase.setTextColor(
                Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)),
            )
            viewModel.next()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.index.collect {
                    binding.textPhrase.text = viewModel.currentPhrase
                }
            }
        }
    }
}
