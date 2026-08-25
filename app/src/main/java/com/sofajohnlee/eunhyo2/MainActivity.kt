package com.sofajohnlee.eunhyo2

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sofajohnlee.eunhyo2.databinding.ActivityMainBinding
import com.sofajohnlee.eunhyo2.feature.english.EnglishStudyActivity
import com.sofajohnlee.eunhyo2.feature.korean.KoreanStudyActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonElementary.setOnClickListener { viewModel.selectGrade(GradeLevel.ELEMENTARY) }
        binding.buttonMiddle.setOnClickListener { viewModel.selectGrade(GradeLevel.MIDDLE) }
        binding.buttonHigh.setOnClickListener { viewModel.selectGrade(GradeLevel.HIGH) }
        binding.buttonEnglishStudy.setOnClickListener {
            startActivity(Intent(this, EnglishStudyActivity::class.java))
        }
        binding.buttonKoreanStudy.setOnClickListener {
            startActivity(Intent(this, KoreanStudyActivity::class.java))
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.textStatus.text = getString(R.string.selected_grade, state.grade.label)
                }
            }
        }
    }
}
