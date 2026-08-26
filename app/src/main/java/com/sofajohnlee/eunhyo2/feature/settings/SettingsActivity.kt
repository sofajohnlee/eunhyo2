package com.sofajohnlee.eunhyo2.feature.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivitySettingsBinding
import com.sofajohnlee.eunhyo2.settings.AppSettings
import com.sofajohnlee.eunhyo2.settings.AppSettingsRepository

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var repository: AppSettingsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repository = AppSettingsRepository(this)

        val settings = repository.load()
        binding.inputFontStyle.setText(settings.fontStyle.toString())
        binding.inputTextColor.setText(settings.textColor.toString())
        binding.inputPenColor.setText(settings.penColor.toString())

        binding.buttonSave.setOnClickListener {
            repository.save(
                AppSettings(
                    fontStyle = binding.inputFontStyle.text.toString().toIntOrNull() ?: 0,
                    textColor = binding.inputTextColor.text.toString().toIntOrNull() ?: 0,
                    penColor = binding.inputPenColor.text.toString().toIntOrNull() ?: 9,
                )
            )
            binding.textResult.text = "설정을 저장했습니다."
        }
        binding.buttonDataTransfer.setOnClickListener {
            startActivity(Intent(this, DataTransferActivity::class.java))
        }
    }
}
