package com.sofajohnlee.eunhyo2.feature.english

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityAlphabetTraceBinding

class AlphabetTraceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlphabetTraceBinding
    private var index = 0
    private var uppercase = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlphabetTraceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonPrevious.setOnClickListener {
            index = (index + 25) % 26
            render()
        }
        binding.buttonNext.setOnClickListener {
            index = (index + 1) % 26
            render()
        }
        binding.buttonCase.setOnClickListener {
            uppercase = !uppercase
            render()
        }
        binding.buttonClear.setOnClickListener { binding.drawingTrace.clear() }
        render()
    }

    private fun render() {
        val letter = ('A'.code + index).toChar()
        val shown = if (uppercase) letter.toString() else letter.lowercase()
        binding.textLetter.text = shown
        binding.drawingTrace.clear()

        val prefix = if (uppercase) "letter_u" else "letter_l"
        val resourceName = prefix + letter.lowercaseChar()
        val resourceId = resources.getIdentifier(resourceName, "drawable", packageName)
        if (resourceId != 0) {
            binding.imageTrace.setImageResource(resourceId)
            binding.textAssetHint.text = "원본 따라쓰기 안내 이미지 사용 중"
        } else {
            binding.imageTrace.setImageDrawable(null)
            binding.textAssetHint.text = "원본 $resourceName drawable을 가져오면 안내 이미지가 표시됩니다."
        }
    }
}
