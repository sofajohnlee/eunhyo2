package com.sofajohnlee.eunhyo2.feature.settings

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityDataTransferBinding
import com.sofajohnlee.eunhyo2.feature.math.MathStateRepository
import java.io.BufferedReader
import java.io.InputStreamReader

/** Modern replacement for Main_env score/state import-export features. */
class DataTransferActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDataTransferBinding
    private lateinit var mathStateRepository: MathStateRepository

    private var pendingExport: ExportKind? = null
    private var pendingImport: ImportKind? = null

    private val createDocument = registerForActivityResult(ActivityResultContracts.CreateDocument("text/plain")) { uri ->
        val kind = pendingExport
        pendingExport = null
        if (uri == null || kind == null) return@registerForActivityResult
        runCatching { writeText(uri, kind.valueProvider()) }
            .onSuccess { toast("내보내기 완료") }
            .onFailure { toast("내보내기 실패") }
    }

    private val openDocument = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        val kind = pendingImport
        pendingImport = null
        if (uri == null || kind == null) return@registerForActivityResult
        runCatching { readInt(uri) }
            .onSuccess { value -> kind.consumer(value); refresh(); toast("가져오기 완료") }
            .onFailure { toast("숫자 형식의 텍스트 파일을 선택해 주세요") }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mathStateRepository = MathStateRepository(this)

        binding.buttonSaveMyScore.setOnClickListener { saveInt(FILE_MY_SCORE, binding.editMyScore.text.toString().toIntOrNull() ?: 2500); refresh() }
        binding.buttonSaveYourScore.setOnClickListener { saveInt(FILE_YOUR_SCORE, binding.editYourScore.text.toString().toIntOrNull() ?: 2700); refresh() }
        binding.buttonSaveState.setOnClickListener { mathStateRepository.save(binding.editState.text.toString().toIntOrNull() ?: 1); refresh() }

        binding.buttonExportMyScore.setOnClickListener { export(ExportKind("mscore.txt") { loadInt(FILE_MY_SCORE, 2500).toString() }) }
        binding.buttonExportYourScore.setOnClickListener { export(ExportKind("yscore.txt") { loadInt(FILE_YOUR_SCORE, 2700).toString() }) }
        binding.buttonExportState.setOnClickListener { export(ExportKind("mstate.txt") { mathStateRepository.load().toString() }) }

        binding.buttonImportMyScore.setOnClickListener { import(ImportKind { saveInt(FILE_MY_SCORE, it) }) }
        binding.buttonImportYourScore.setOnClickListener { import(ImportKind { saveInt(FILE_YOUR_SCORE, it) }) }
        binding.buttonImportState.setOnClickListener { import(ImportKind { mathStateRepository.save(it) }) }
        refresh()
    }

    private fun refresh() {
        binding.editMyScore.setText(loadInt(FILE_MY_SCORE, 2500).toString())
        binding.editYourScore.setText(loadInt(FILE_YOUR_SCORE, 2700).toString())
        binding.editState.setText(mathStateRepository.load().toString())
    }

    private fun export(kind: ExportKind) {
        pendingExport = kind
        createDocument.launch(kind.fileName)
    }

    private fun import(kind: ImportKind) {
        pendingImport = kind
        openDocument.launch(arrayOf("text/plain", "text/*"))
    }

    private fun saveInt(name: String, value: Int) = openFileOutput(name, MODE_PRIVATE).bufferedWriter().use { it.write(value.toString()) }

    private fun loadInt(name: String, default: Int): Int = runCatching {
        openFileInput(name).bufferedReader().use { it.readLine()?.trim()?.toIntOrNull() ?: default }
    }.getOrDefault(default)

    private fun writeText(uri: Uri, value: String) {
        contentResolver.openOutputStream(uri, "w")?.bufferedWriter()?.use { it.write(value) }
            ?: error("output stream unavailable")
    }

    private fun readInt(uri: Uri): Int {
        val text = contentResolver.openInputStream(uri)?.use { input ->
            BufferedReader(InputStreamReader(input, Charsets.UTF_8)).readLine()
        } ?: error("input stream unavailable")
        return text.trim().toInt()
    }

    private fun toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    private data class ExportKind(val fileName: String, val valueProvider: () -> String)
    private data class ImportKind(val consumer: (Int) -> Unit)

    companion object {
        private const val FILE_MY_SCORE = "mscore.txt"
        private const val FILE_YOUR_SCORE = "yscore.txt"
    }
}
