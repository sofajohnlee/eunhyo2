package com.sofajohnlee.eunhyo2.feature.goldenbell

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityGoldenBellBinding
import com.sofajohnlee.eunhyo2.speech.SpeechController
import kotlin.random.Random

class GoldenBellActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGoldenBellBinding
    private lateinit var speech: SpeechController
    private var current: GoldenBellQuestion = GoldenBellRepository.questions.first()
    private var score = 0
    private var attempts = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoldenBellBinding.inflate(layoutInflater)
        setContentView(binding.root)
        speech = SpeechController(this)

        binding.buttonO.setOnClickListener { answer(true) }
        binding.buttonX.setOnClickListener { answer(false) }
        binding.buttonNextQuestion.setOnClickListener { nextQuestion() }
        binding.buttonReadQuestion.setOnClickListener { speech.speak(current.text, "ko-KR") }
        nextQuestion()
    }

    private fun answer(value: Boolean) {
        attempts++
        val correct = value == current.answer
        if (correct) score++
        binding.textGoldenBellFeedback.text = if (correct) "정답입니다." else "오답입니다."
        binding.textGoldenBellScore.text = "$score / $attempts"
    }

    private fun nextQuestion() {
        current = GoldenBellRepository.questions[Random.nextInt(GoldenBellRepository.questions.size)]
        binding.textGoldenBellQuestion.text = current.text
        binding.textGoldenBellFeedback.text = ""
    }

    override fun onDestroy() {
        speech.shutdown()
        super.onDestroy()
    }
}
