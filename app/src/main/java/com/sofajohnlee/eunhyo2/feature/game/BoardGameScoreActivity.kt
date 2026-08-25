package com.sofajohnlee.eunhyo2.feature.game

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.sofajohnlee.eunhyo2.databinding.ActivityBoardGameScoreBinding

class BoardGameScoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBoardGameScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardGameScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val refresh = {
            val k1 = binding.editKnight1.text?.toString()?.toIntOrNull() ?: 0
            val k2 = binding.editKnight2.text?.toString()?.toIntOrNull() ?: 0
            val farmer = binding.editFarmer.text?.toString()?.toIntOrNull() ?: 0
            binding.textKnightScore.text = BoardGameScoreCalculator.knight(k1, k2, binding.checkDoubleKnight.isChecked).toString()
            binding.textFarmerScore.text = BoardGameScoreCalculator.farmer(farmer).toString()
        }
        binding.editKnight1.doAfterTextChanged { refresh() }
        binding.editKnight2.doAfterTextChanged { refresh() }
        binding.editFarmer.doAfterTextChanged { refresh() }
        binding.checkDoubleKnight.setOnCheckedChangeListener { _, _ -> refresh() }
        refresh()
    }
}
