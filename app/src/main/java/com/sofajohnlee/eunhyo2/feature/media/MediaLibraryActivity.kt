package com.sofajohnlee.eunhyo2.feature.media

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityMediaLibraryBinding

class MediaLibraryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMediaLibraryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = MediaCatalog.courageVideos
        binding.listMedia.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            items.map { it.title },
        )
        binding.listMedia.setOnItemClickListener { _, _, position, _ ->
            openYoutube(items[position].videoId)
        }
    }

    private fun openYoutube(videoId: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$videoId"))
        runCatching { startActivity(appIntent) }
            .onFailure { startActivity(webIntent) }
    }
}
