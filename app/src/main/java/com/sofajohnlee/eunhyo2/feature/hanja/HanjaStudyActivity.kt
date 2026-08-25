package com.sofajohnlee.eunhyo2.feature.hanja

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sofajohnlee.eunhyo2.databinding.ActivityHanjaStudyBinding
import kotlinx.coroutines.launch

class HanjaStudyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHanjaStudyBinding
    private val viewModel: HanjaStudyViewModel by viewModels()

    private val csvPicker = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri == null) return@registerForActivityResult
        runCatching {
            contentResolver.openInputStream(uri)?.use(viewModel::importCsv) ?: 0
        }.onSuccess { count ->
            Toast.makeText(
                this,
                if (count > 0) "한자 ${count}자를 가져왔습니다." else "읽을 수 있는 한자 데이터가 없습니다.",
                Toast.LENGTH_SHORT,
            ).show()
        }.onFailure {
            Toast.makeText(this, "CSV 파일을 읽지 못했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHanjaStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonReveal.setOnClickListener { viewModel.toggleReveal() }
        binding.buttonNext.setOnClickListener { viewModel.next() }
        binding.buttonImport.setOnClickListener {
            csvPicker.launch(arrayOf("text/csv", "text/comma-separated-values", "text/plain"))
        }
        binding.buttonBuiltIn.setOnClickListener { viewModel.restoreBuiltIn() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.textHanja.text = state.current.character
                    binding.textReading.text = if (state.reveal) state.current.reading else ""
                    binding.textMeaning.text = if (state.reveal) state.current.meaning else ""
                    binding.textSource.text = state.sourceLabel
                }
            }
        }
    }
}
