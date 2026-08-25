package com.sofajohnlee.eunhyo2.feature.clock

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sofajohnlee.eunhyo2.databinding.ActivityClockStudyBinding
import kotlinx.coroutines.launch

class ClockStudyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClockStudyBinding
    private val viewModel: ClockStudyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClockStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonHour.setOnClickListener { viewModel.plusHour() }
        binding.button30.setOnClickListener { viewModel.plus30() }
        binding.button10.setOnClickListener { viewModel.plus10() }
        binding.button5.setOnClickListener { viewModel.plus5() }
        binding.buttonReset.setOnClickListener { viewModel.reset() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.textTime.text = state.label
                    binding.clockView.setTime(state.hour, state.minute)
                }
            }
        }
    }
}
