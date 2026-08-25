package com.sofajohnlee.eunhyo2.feature.hanja

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityHanjaRadicalBinding

class HanjaRadicalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHanjaRadicalBinding
    private lateinit var dataStore: HanjaRadicalDataStore
    private val importer = HanjaRadicalImporter()
    private var groups: List<RadicalGroup> = emptyList()
    private var groupIndex = 0
    private var entryIndex = 0

    private val openCsvFiles = registerForActivityResult(ActivityResultContracts.OpenMultipleDocuments()) { uris ->
        if (uris.isEmpty()) return@registerForActivityResult
        val sources = uris.mapNotNull { uri ->
            val input = contentResolver.openInputStream(uri) ?: return@mapNotNull null
            HanjaRadicalImporter.Source(displayName(uri), input)
        }
        val imported = importer.import(sources)
        if (imported.isNotEmpty()) {
            groups = imported
            dataStore.save(imported)
            groupIndex = 0
            entryIndex = 0
            render("${imported.size}개 부수 파일을 가져왔습니다.")
        } else {
            render("가져올 수 있는 한자 데이터가 없습니다.")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHanjaRadicalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataStore = HanjaRadicalDataStore(this)
        groups = dataStore.load().ifEmpty { HanjaRadicalRepository.groups }

        binding.buttonImport.setOnClickListener {
            openCsvFiles.launch(arrayOf("text/*", "application/csv", "application/vnd.ms-excel"))
        }
        binding.buttonNextEntry.setOnClickListener {
            val group = currentGroup()
            entryIndex = (entryIndex + 1) % group.entries.size
            render()
        }
        binding.buttonNextGroup.setOnClickListener {
            groupIndex = (groupIndex + 1) % groups.size
            entryIndex = 0
            render()
        }
        binding.buttonReset.setOnClickListener {
            dataStore.clear()
            groups = HanjaRadicalRepository.groups
            groupIndex = 0
            entryIndex = 0
            render("내장 부수 데이터로 되돌렸습니다.")
        }
        render()
    }

    private fun currentGroup(): RadicalGroup = groups[groupIndex.coerceIn(groups.indices)]

    private fun render(message: String? = null) {
        val group = currentGroup()
        val entry = group.entries[entryIndex.coerceIn(group.entries.indices)]
        binding.textGroup.text = "${group.title} (${groupIndex + 1}/${groups.size})"
        binding.textCharacter.text = entry.character
        binding.textReading.text = entry.reading
        binding.textMeaning.text = entry.meaning
        binding.textStatus.text = message ?: "${entryIndex + 1}/${group.entries.size} · 총 ${groups.sumOf { it.entries.size }}자"
    }

    private fun displayName(uri: Uri): String {
        var cursor: Cursor? = null
        return try {
            cursor = contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
            if (cursor != null && cursor.moveToFirst()) cursor.getString(0) else uri.lastPathSegment.orEmpty()
        } finally {
            cursor?.close()
        }
    }
}
