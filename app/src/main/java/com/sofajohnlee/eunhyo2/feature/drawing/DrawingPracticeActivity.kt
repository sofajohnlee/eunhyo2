package com.sofajohnlee.eunhyo2.feature.drawing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityDrawingPracticeBinding

class DrawingPracticeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDrawingPracticeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawingPracticeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonClear.setOnClickListener { binding.drawingView.clear() }
    }
}
