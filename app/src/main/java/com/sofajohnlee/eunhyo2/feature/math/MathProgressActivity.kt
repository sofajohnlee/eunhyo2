package com.sofajohnlee.eunhyo2.feature.math

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityMathProgressBinding

class MathProgressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMathProgressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMathProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        render()
        binding.buttonRefresh.setOnClickListener { render() }
    }

    override fun onResume() {
        super.onResume()
        render()
    }

    private fun render() {
        val progress = MathProgressRepository(this).load()
        val state = MathStateRepository(this).load()
        binding.textCorrect.text = "정답: ${progress.correct}"
        binding.textAttempts.text = "시도: ${progress.attempts}"
        binding.textAccuracy.text = "정답률: ${progress.accuracyPercent}%"
        binding.textState.text = "학습 상태: ${stateLabel(state)}"
    }

    private fun stateLabel(value: Int): String = when (value) {
        1 -> "1 · 집중"
        2 -> "2 · 보통"
        3 -> "3 · 복습 필요"
        4 -> "4 · 휴식 권장"
        else -> "0 · 미설정"
    }
}
