package com.sofajohnlee.eunhyo2.feature.english

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityEnglishSentenceImportBinding

class EnglishSentenceImportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnglishSentenceImportBinding
    private val parser = EnglishSentenceCsvParser()
    private lateinit var store: EnglishSentenceSelectionStore
    private var imported: List<EnglishSentenceEntry> = emptyList()

    private val openDocument = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri == null) return@registerForActivityResult
        imported = runCatching {
            contentResolver.openInputStream(uri)?.use(parser::parse).orEmpty()
        }.getOrElse { emptyList() }
        renderImported()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnglishSentenceImportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        store = EnglishSentenceSelectionStore(this)

        binding.buttonChooseCsv.setOnClickListener {
            openDocument.launch(arrayOf("text/*", "application/csv", "application/vnd.ms-excel"))
        }
        binding.buttonSaveImported.setOnClickListener {
            store.save(imported)
            binding.textImportStatus.text = "${imported.size}개 문장을 앱 내부에 저장했습니다."
        }

        val saved = store.load()
        if (saved.isNotEmpty()) {
            imported = saved
            binding.textImportStatus.text = "저장된 문장 ${saved.size}개를 불러왔습니다."
            renderImported()
        }
    }

    private fun renderImported() {
        binding.buttonSaveImported.isEnabled = imported.isNotEmpty()
        binding.textImportStatus.text = if (imported.isEmpty()) "유효한 문장을 찾지 못했습니다." else "${imported.size}개 문장을 가져왔습니다."
        binding.textImportedPreview.text = imported.take(10).joinToString("\n\n") {
            "${it.sentence}\n${it.meaning}${if (it.note.isBlank()) "" else "\n${it.note}"}"
        }
    }
}
