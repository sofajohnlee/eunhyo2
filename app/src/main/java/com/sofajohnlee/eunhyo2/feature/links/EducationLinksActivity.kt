package com.sofajohnlee.eunhyo2.feature.links

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityEducationLinksBinding

class EducationLinksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEducationLinksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEducationLinksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCyberSeodang.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.cyberseodang.or.kr/")))
        }
    }
}
