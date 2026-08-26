package com.sofajohnlee.eunhyo2.feature.notebook

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityStudyMailBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Modern replacement for MainMathPlusSbEmail.
 *
 * Legacy code read/wrote dated text files directly under shared external
 * storage. This version keeps outgoing notes in app-private storage, imports
 * received text through SAF, and preserves TTS and speech-input behavior.
 */
class StudyMailActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityStudyMailBinding
    private var tts: TextToSpeech? = null

    private val importText = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri == null) return@registerForActivityResult
        runCatching {
            contentResolver.openInputStream(uri)?.bufferedReader(Charsets.UTF_8)?.use { it.readText() }.orEmpty()
        }.onSuccess { text ->
            binding.textReceived.text = text.ifBlank { "파일에 내용이 없습니다." }
        }.onFailure {
            Toast.makeText(this, "받은 편지 파일을 읽지 못했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private val speechInput = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val spoken = result.data
            ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            ?.firstOrNull()
            .orEmpty()
        if (spoken.isNotBlank()) {
            val current = binding.editOutgoing.text?.toString().orEmpty()
            binding.editOutgoing.setText(listOf(current, spoken).filter { it.isNotBlank() }.joinToString(" "))
            binding.editOutgoing.setSelection(binding.editOutgoing.text?.length ?: 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudyMailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tts = TextToSpeech(this, this)
        loadTodayOutgoing()

        binding.buttonImportReceived.setOnClickListener { importText.launch("text/*") }
        binding.buttonReadReceived.setOnClickListener {
            speak(binding.textReceived.text?.toString().orEmpty())
        }
        binding.buttonVoiceInput.setOnClickListener { startSpeechInput() }
        binding.buttonSaveOutgoing.setOnClickListener { saveOutgoing() }
        binding.buttonClearOutgoing.setOnClickListener { binding.editOutgoing.text?.clear() }
    }

    private fun todayKey(): String = SimpleDateFormat("yyyyMMdd", Locale.US).format(Date())

    private fun outgoingFile() = fileStreamPath("omail${todayKey()}.txt")

    private fun loadTodayOutgoing() {
        val file = outgoingFile()
        if (file.exists()) binding.editOutgoing.setText(file.readText(Charsets.UTF_8))
        binding.textDate.text = "오늘 ${todayKey()}"
    }

    private fun saveOutgoing() {
        val text = binding.editOutgoing.text?.toString().orEmpty().trim()
        if (text.isBlank()) {
            Toast.makeText(this, "저장할 내용이 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        runCatching {
            outgoingFile().writeText(text + "\n", Charsets.UTF_8)
        }.onSuccess {
            Toast.makeText(this, "오늘의 학습 편지를 저장했습니다.", Toast.LENGTH_SHORT).show()
        }.onFailure {
            Toast.makeText(this, "편지를 저장하지 못했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault().toLanguageTag())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "학습 편지를 말해 주세요")
        }
        runCatching { speechInput.launch(intent) }
            .onFailure { Toast.makeText(this, "음성 입력을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show() }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.language = Locale.getDefault()
        }
    }

    private fun speak(text: String) {
        if (text.isBlank()) return
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "study-mail")
    }

    override fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        super.onDestroy()
    }
}
