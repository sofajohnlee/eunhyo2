package com.sofajohnlee.eunhyo2.feature.english

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityEnglishWordPracticeBinding

class EnglishWordPracticeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnglishWordPracticeBinding
    private val words = EnglishWordCatalog.all()
    private var index = 0
    private var capitalized = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnglishWordPracticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCase.setOnClickListener {
            capitalized = !capitalized
            render()
        }
        binding.buttonPrevious.setOnClickListener {
            index = (index - 1 + words.size) % words.size
            render()
        }
        binding.buttonNext.setOnClickListener {
            index = (index + 1) % words.size
            render()
        }
        render()
    }

    private fun render() {
        binding.textWord.text = EnglishWordCatalog.display(words[index], capitalized)
        binding.textPosition.text = "${index + 1} / ${words.size}"
        binding.buttonCase.text = if (capitalized) "소문자로 보기" else "대문자로 시작"
    }
}
