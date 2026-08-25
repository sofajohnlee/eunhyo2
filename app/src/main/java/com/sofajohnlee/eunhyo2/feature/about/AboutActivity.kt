package com.sofajohnlee.eunhyo2.feature.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.BuildConfig
import com.sofajohnlee.eunhyo2.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textVersion.text = "버전 ${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
        binding.textEnvironment.text = "Android Studio Meerkat | 2024.3.1\nAGP 8.9.2 · Gradle 8.11.1 · JDK 17\ncompileSdk/targetSdk 35"
    }
}
