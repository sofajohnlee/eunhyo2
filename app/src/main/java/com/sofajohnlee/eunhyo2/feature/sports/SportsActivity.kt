package com.sofajohnlee.eunhyo2.feature.sports

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivitySportsBinding

class SportsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySportsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySportsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val videos = SportsCatalog.videos
        binding.buttonJumpRope.setOnClickListener { open(videos[0]) }
        binding.buttonBadminton.setOnClickListener { open(videos[1]) }
        binding.buttonTableTennis.setOnClickListener { open(videos[2]) }
    }

    private fun open(video: SportsVideo) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:${video.videoId}"))
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=${video.videoId}"))
        runCatching { startActivity(appIntent) }.getOrElse { startActivity(webIntent) }
    }
}
