package com.sofajohnlee.eunhyo2.feature.history

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityHistoryStudyBinding

class HistoryStudyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryStudyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eras = HistoryEra.entries
        binding.spinnerEra.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            eras.map { it.label },
        )
        binding.spinnerEra.setSelection(0)
        binding.spinnerEra.onItemSelectedListener = SimpleItemSelectedListener { position ->
            render(eras[position])
        }
        render(eras.first())
    }

    private fun render(era: HistoryEra) {
        binding.textHistory.text = HistoryRepository.entries(era).joinToString("\n\n") { entry ->
            buildString {
                append(entry.title)
                if (entry.period.isNotBlank()) append(" · ${entry.period}")
                if (entry.societyCulture.isNotBlank()) append("\n사회·문화: ${entry.societyCulture}")
                if (entry.economy.isNotBlank()) append("\n경제: ${entry.economy}")
                if (entry.politics.isNotBlank()) append("\n정치: ${entry.politics}")
            }
        }
    }
}
