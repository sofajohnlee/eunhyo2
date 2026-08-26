package com.sofajohnlee.eunhyo2.feature.typing

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityTypingPracticeBinding

class TypingPracticeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTypingPracticeBinding
    private var language = TypingLanguage.KOREAN
    private var lessonIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTypingPracticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLanguage.setOnClickListener {
            language = if (language == TypingLanguage.KOREAN) TypingLanguage.ENGLISH else TypingLanguage.KOREAN
            lessonIndex = 0
            renderLesson(clearInput = true)
        }
        binding.buttonNextLesson.setOnClickListener {
            val lessons = TypingPracticeCatalog.lessons(language)
            lessonIndex = (lessonIndex + 1) % lessons.size
            renderLesson(clearInput = true)
        }
        binding.buttonClear.setOnClickListener { binding.editTyped.text?.clear() }
        binding.editTyped.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = updateAccuracy()
            override fun afterTextChanged(s: Editable?) = Unit
        })

        renderLesson(clearInput = false)
    }

    private fun renderLesson(clearInput: Boolean) {
        val lesson = TypingPracticeCatalog.lessons(language)[lessonIndex]
        binding.buttonLanguage.text = if (language == TypingLanguage.KOREAN) "한글 모드 → 영문 전환" else "영문 모드 → 한글 전환"
        binding.textLessonTitle.text = lesson.title
        binding.textTarget.text = lesson.target
        if (clearInput) binding.editTyped.text?.clear()
        updateAccuracy()
    }

    private fun updateAccuracy() {
        val lesson = TypingPracticeCatalog.lessons(language)[lessonIndex]
        val typed = binding.editTyped.text?.toString().orEmpty()
        val accuracy = TypingPracticeCatalog.accuracy(lesson.target, typed)
        binding.textResult.text = "정확도 $accuracy% · ${typed.length}/${lesson.target.length}자"
    }
}
