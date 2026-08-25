package com.sofajohnlee.eunhyo2.feature.about

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        val versionName = packageInfo.versionName ?: "-"
        val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.longVersionCode
        } else {
            @Suppress("DEPRECATION")
            packageInfo.versionCode.toLong()
        }
        binding.textVersion.text = "버전 $versionName ($versionCode)"
        binding.textEnvironment.text = "Android Studio Meerkat | 2024.3.1\nAGP 8.9.2 · Gradle 8.11.1 · JDK 17\ncompileSdk/targetSdk 35"
    }
}
