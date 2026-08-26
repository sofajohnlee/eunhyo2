package com.sofajohnlee.eunhyo2.feature.english

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityPhonicsColorBinding
import java.util.Locale

class PhonicsColorActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityPhonicsColorBinding
    private var tts: TextToSpeech? = null
    private var groups: List<Pair<String, List<String>>> = emptyList()
    private var groupIndex = 0
    private var wordIndex = 0
    private var sourceLabel = "기본 데이터"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhonicsColorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tts = TextToSpeech(this, this)
        groups = loadGroups()

        binding.buttonPreviousGroup.setOnClickListener { moveGroup(-1) }
        binding.buttonNextGroup.setOnClickListener { moveGroup(1) }
        binding.buttonPreviousWord.setOnClickListener { moveWord(-1) }
        binding.buttonNextWord.setOnClickListener { moveWord(1) }
        binding.buttonSpeak.setOnClickListener { speakCurrent() }
        render()
    }

    private fun loadGroups(): List<Pair<String, List<String>>> {
        val rows = runCatching {
            assets.open("legacy_phonics.csv").bufferedReader().useLines { lines ->
                lines.drop(1).mapNotNull { line ->
                    val parts = line.split(',', limit = 2)
                    if (parts.size == 2) parts[0].trim() to parts[1].trim() else null
                }.toList()
            }
        }.getOrDefault(emptyList())

        if (rows.isNotEmpty()) {
            sourceLabel = "원본 MainEengAgent 데이터 ${rows.size}개"
            return rows.groupBy({ it.first }, { it.second }).map { it.key to it.value }
        }
        sourceLabel = "기본 데이터 · 원본 import 시 자동 확장"
        return listOf(
            "black" to listOf("black", "cat", "apple"),
            "red" to listOf("red", "dress", "elephant"),
            "green" to listOf("green", "tea", "feet"),
            "blue" to listOf("blue", "moon", "zoo"),
        )
    }

    private fun moveGroup(delta: Int) {
        if (groups.isEmpty()) return
        groupIndex = (groupIndex + delta + groups.size) % groups.size
        wordIndex = 0
        render()
    }

    private fun moveWord(delta: Int) {
        val words = groups.getOrNull(groupIndex)?.second.orEmpty()
        if (words.isEmpty()) return
        wordIndex = (wordIndex + delta + words.size) % words.size
        render()
    }

    private fun render() {
        val current = groups.getOrNull(groupIndex) ?: return
        val words = current.second
        if (words.isEmpty()) return
        binding.textGroup.text = "색상 그룹: ${current.first} (${wordIndex + 1}/${words.size})"
        binding.textWord.text = words[wordIndex]
        binding.textSource.text = sourceLabel
    }

    private fun speakCurrent() {
        val word = groups.getOrNull(groupIndex)?.second?.getOrNull(wordIndex) ?: return
        tts?.speak(word, TextToSpeech.QUEUE_FLUSH, null, "phonics-word")
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) tts?.language = Locale.US
    }

    override fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        super.onDestroy()
    }
}
