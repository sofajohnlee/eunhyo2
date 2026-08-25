package com.sofajohnlee.eunhyo2.feature.geometry

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sofajohnlee.eunhyo2.databinding.ActivityGeometryStudyBinding
import kotlinx.coroutines.launch

class GeometryStudyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGeometryStudyBinding
    private val viewModel: GeometryStudyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeometryStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCircle.setOnClickListener { viewModel.select(GeometryCategory.CIRCLE) }
        binding.buttonTriangle.setOnClickListener { viewModel.select(GeometryCategory.TRIANGLE) }
        binding.buttonRectangle.setOnClickListener { viewModel.select(GeometryCategory.RECTANGLE) }
        binding.buttonSolid.setOnClickListener { viewModel.select(GeometryCategory.SOLID) }
        binding.buttonPrevious.setOnClickListener { viewModel.previous() }
        binding.buttonNext.setOnClickListener { viewModel.next() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.textGeometryTitle.text = "${state.category.label} · ${state.item.title}"
                    binding.textGeometryDescription.text = state.item.description
                    binding.geometryCanvas.category = state.category
                    binding.geometryCanvas.title = state.item.title
                }
            }
        }
    }
}
