package com.sofajohnlee.eunhyo2.feature.personality

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityPersonalityQuizBinding

class PersonalityQuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonalityQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonResult.setOnClickListener {
            val answers = PersonalityAnswers(
                q1 = binding.switchQ1.isChecked,
                q2 = binding.switchQ2.isChecked,
                q3 = binding.switchQ3.isChecked,
                q4 = binding.switchQ4.isChecked,
            )
            binding.textResult.text = PersonalityQuiz.result(answers)
        }
    }
}
