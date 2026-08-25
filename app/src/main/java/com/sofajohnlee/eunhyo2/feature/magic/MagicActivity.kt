package com.sofajohnlee.eunhyo2.feature.magic

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityMagicBinding

class MagicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMagicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMagicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MagicCatalog.videos.forEachIndexed { index, video ->
            val button = Button(this).apply {
                text = "${index + 1}. ${video.title}"
                setOnClickListener { openVideo(video.videoId) }
            }
            binding.magicContainer.addView(
                button,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                ),
            )
        }
    }

    private fun openVideo(videoId: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$videoId"))
        runCatching { startActivity(appIntent) }
            .onFailure { startActivity(webIntent) }
    }
}
