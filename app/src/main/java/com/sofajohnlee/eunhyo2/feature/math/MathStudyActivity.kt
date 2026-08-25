package com.sofajohnlee.eunhyo2.feature.math

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sofajohnlee.eunhyo2.databinding.ActivityMathStudyBinding
import kotlinx.coroutines.launch

class MathStudyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMathStudyBinding
    private val viewModel: MathStudyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMathStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonNatural.setOnClickListener { viewModel.selectNumberMode(NumberMode.NATURAL) }
        binding.buttonDecimal.setOnClickListener { viewModel.selectNumberMode(NumberMode.DECIMAL) }
        binding.buttonFraction.setOnClickListener { viewModel.selectNumberMode(NumberMode.FRACTION) }

        binding.buttonBeginner.setOnClickListener { viewModel.selectDifficulty(MathDifficulty.BEGINNER) }
        binding.buttonIntermediate.setOnClickListener { viewModel.selectDifficulty(MathDifficulty.INTERMEDIATE) }
        binding.buttonAdvanced.setOnClickListener { viewModel.selectDifficulty(MathDifficulty.ADVANCED) }

        binding.buttonAdd.setOnClickListener { viewModel.selectOperation(MathOperation.ADD) }
        binding.buttonSubtract.setOnClickListener { viewModel.selectOperation(MathOperation.SUBTRACT) }
        binding.buttonMultiply.setOnClickListener { viewModel.selectOperation(MathOperation.MULTIPLY) }
        binding.buttonDivide.setOnClickListener { viewModel.selectOperation(MathOperation.DIVIDE) }

        binding.editAnswer.doAfterTextChanged { viewModel.updateInput(it?.toString().orEmpty()) }
        binding.buttonSubmit.setOnClickListener { viewModel.submit() }
        binding.buttonNext.setOnClickListener {
            binding.editAnswer.text?.clear()
            viewModel.next()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.textProblem.text = state.exercise.expression
                    binding.textFeedback.text = state.feedback
                    binding.textScore.text = state.scoreLabel
                }
            }
        }
    }
}
