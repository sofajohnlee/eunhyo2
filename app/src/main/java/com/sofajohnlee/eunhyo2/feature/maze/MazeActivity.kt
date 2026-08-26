package com.sofajohnlee.eunhyo2.feature.maze

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityMazeBinding

class MazeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMazeBinding
    private var state = MazeState(MazeCatalog.level(1))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMazeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonMaze1.setOnClickListener { loadLevel(1) }
        binding.buttonMaze2.setOnClickListener { loadLevel(2) }
        binding.buttonMaze3.setOnClickListener { loadLevel(3) }
        binding.buttonUp.setOnClickListener { move(MazeDirection.UP) }
        binding.buttonDown.setOnClickListener { move(MazeDirection.DOWN) }
        binding.buttonLeft.setOnClickListener { move(MazeDirection.LEFT) }
        binding.buttonRight.setOnClickListener { move(MazeDirection.RIGHT) }

        render()
    }

    private fun loadLevel(number: Int) {
        state = MazeState(MazeCatalog.level(number))
        render()
    }

    private fun move(direction: MazeDirection) {
        state.move(direction)
        render()
    }

    private fun render() {
        binding.mazeBoard.state = state
        binding.textMazeStatus.text = if (state.complete) {
            "미로 ${state.level.number} 완주!"
        } else {
            "미로 ${state.level.number} · (${state.x + 1}, ${state.y + 1})"
        }
    }
}
