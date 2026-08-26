package com.sofajohnlee.eunhyo2.feature.korean

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityMultilingualPictureStudyBinding
import java.util.Locale

class MultilingualPictureStudyActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityMultilingualPictureStudyBinding
    private var tts: TextToSpeech? = null
    private var index = 0
    private var cards: List<Card> = emptyList()

    data class Card(val korean: String, val english: String, val french: String, val image: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultilingualPictureStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tts = TextToSpeech(this, this)
        cards = loadCards().ifEmpty {
            listOf(
                Card("가방", "bag", "sac", "kor0"),
                Card("나비", "ball", "papillon", "kor1"),
                Card("다람쥐", "candle", "écureuil", "kor2"),
            )
        }
        binding.buttonPrevious.setOnClickListener { index = if (index == 0) cards.lastIndex else index - 1; render() }
        binding.buttonNext.setOnClickListener { index = (index + 1) % cards.size; render() }
        binding.buttonSpeak.setOnClickListener { speakCurrent() }
        render()
    }

    private fun loadCards(): List<Card> = runCatching {
        assets.open("korean/legacy_kgkorean.csv").bufferedReader(Charsets.UTF_8).useLines { lines ->
            lines.drop(1).mapNotNull { line ->
                val p = parseCsv(line)
                if (p.size < 5) null else Card(p[1], p[2], p[3], p[4])
            }.toList()
        }
    }.getOrDefault(emptyList())

    private fun parseCsv(line: String): List<String> {
        val result = mutableListOf<String>()
        val current = StringBuilder()
        var quoted = false
        var i = 0
        while (i < line.length) {
            val c = line[i]
            when {
                c == '"' && quoted && i + 1 < line.length && line[i + 1] == '"' -> { current.append('"'); i++ }
                c == '"' -> quoted = !quoted
                c == ',' && !quoted -> { result += current.toString(); current.clear() }
                else -> current.append(c)
            }
            i++
        }
        result += current.toString()
        return result
    }

    private fun render() {
        val card = cards[index]
        binding.textKorean.text = card.korean
        binding.textEnglish.text = card.english
        binding.textFrench.text = card.french
        val resId = resources.getIdentifier(card.image, "drawable", packageName)
        if (resId != 0) binding.imageCard.setImageResource(resId) else binding.imageCard.setImageDrawable(null)
        binding.textPosition.text = "${index + 1} / ${cards.size}"
    }

    private fun speakCurrent() {
        val card = cards[index]
        val language = when (binding.languageGroup.checkedRadioButtonId) {
            binding.radioEnglish.id -> Locale.US
            binding.radioFrench.id -> Locale.FRENCH
            else -> Locale.KOREAN
        }
        val text = when (language.language) {
            Locale.ENGLISH.language -> card.english
            Locale.FRENCH.language -> card.french
            else -> card.korean
        }
        tts?.language = language
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "multilingual-picture")
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) tts?.language = Locale.KOREAN
    }

    override fun onDestroy() {
        tts?.shutdown()
        super.onDestroy()
    }
}
