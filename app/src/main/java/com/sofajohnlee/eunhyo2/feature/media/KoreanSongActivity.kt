package com.sofajohnlee.eunhyo2.feature.media

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityKoreanSongBinding

class KoreanSongActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKoreanSongBinding
    private var player: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKoreanSongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonPlay.setOnClickListener { play() }
        binding.buttonPause.setOnClickListener { player?.takeIf { it.isPlaying }?.pause() }
        binding.buttonStop.setOnClickListener { releasePlayer() }
        updateAvailability()
    }

    private fun songResourceId(): Int = resources.getIdentifier("song1", "raw", packageName)

    private fun updateAvailability() {
        binding.textStatus.text = if (songResourceId() != 0) {
            "원본 song1.mp3를 사용할 수 있습니다."
        } else {
            "원본 song1.mp3가 아직 복사되지 않았습니다. tools/import_legacy_binary_assets.sh 실행 후 사용할 수 있습니다."
        }
    }

    private fun play() {
        val resId = songResourceId()
        if (resId == 0) {
            updateAvailability()
            return
        }
        if (player == null) {
            player = MediaPlayer.create(this, resId)?.apply {
                isLooping = true
                setVolume(0.8f, 0.8f)
            }
        }
        player?.start()
        binding.textStatus.text = "재생 중"
    }

    private fun releasePlayer() {
        player?.release()
        player = null
        updateAvailability()
    }

    override fun onDestroy() {
        releasePlayer()
        super.onDestroy()
    }
}
