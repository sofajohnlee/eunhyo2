package com.sofajohnlee.eunhyo2.feature.math

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityMathStateBinding

class MathStateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMathStateBinding
    private lateinit var repository: MathStateRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMathStateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repository = MathStateRepository(this)

        render(repository.load())
        binding.buttonState0.setOnClickListener { save(0) }
        binding.buttonState1.setOnClickListener { save(1) }
        binding.buttonState2.setOnClickListener { save(2) }
        binding.buttonState3.setOnClickListener { save(3) }
        binding.buttonState4.setOnClickListener { save(4) }
    }

    private fun save(value: Int) {
        repository.save(value)
        render(value)
    }

    private fun render(value: Int) {
        binding.textCurrentState.text = "현재 수학 상태: $value"
    }
}
