package com.sofajohnlee.eunhyo2.feature.legacy

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityLegacySlideshowBinding

class LegacySlideshowActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLegacySlideshowBinding
    private val handler = Handler(Looper.getMainLooper())
    private var index = 0
    private var autoPlay = false
    private var player: MediaPlayer? = null
    private var setIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLegacySlideshowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSet.setOnClickListener {
            setIndex = (setIndex + 1) % SETS.size
            index = 0
            stopAudio()
            render()
        }
        binding.buttonPrevious.setOnClickListener { move(-1) }
        binding.buttonNext.setOnClickListener { move(1) }
        binding.buttonAuto.setOnClickListener {
            autoPlay = !autoPlay
            binding.buttonAuto.text = if (autoPlay) "자동 넘김 끄기" else "자동 넘김 켜기"
            if (autoPlay) scheduleNext() else handler.removeCallbacksAndMessages(null)
        }
        binding.buttonSound.setOnClickListener {
            if (player == null) startAudio() else stopAudio()
        }
        render()
    }

    private fun move(delta: Int) {
        val items = SETS[setIndex].images
        index = (index + delta + items.size) % items.size
        render()
    }

    private fun render() {
        val set = SETS[setIndex]
        binding.textTitle.text = set.title
        val resourceName = set.images[index]
        val id = resources.getIdentifier(resourceName, "drawable", packageName)
        if (id == 0) {
            binding.imageSlide.setImageDrawable(null)
            binding.textStatus.text = "원본 자산 '$resourceName'이 없습니다. 자산 가져오기 스크립트 실행 후 표시됩니다."
        } else {
            binding.imageSlide.setImageResource(id)
            binding.textStatus.text = "${index + 1} / ${set.images.size}"
        }
    }

    private fun scheduleNext() {
        handler.removeCallbacksAndMessages(null)
        if (!autoPlay) return
        handler.postDelayed({
            if (autoPlay) {
                move(1)
                scheduleNext()
            }
        }, 2500L)
    }

    private fun startAudio() {
        val rawName = SETS[setIndex].audioRawName
        val id = resources.getIdentifier(rawName, "raw", packageName)
        if (id == 0) {
            binding.textStatus.text = "배경음 '$rawName'이 없습니다. 원본/외부 오디오를 raw 자산으로 가져오면 재생됩니다."
            return
        }
        player = MediaPlayer.create(this, id)?.apply {
            isLooping = true
            start()
        }
        binding.buttonSound.text = "배경음 끄기"
    }

    private fun stopAudio() {
        player?.stop()
        player?.release()
        player = null
        binding.buttonSound.text = "배경음 켜기"
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        stopAudio()
        super.onDestroy()
    }

    data class SlideSet(
        val title: String,
        val images: List<String>,
        val audioRawName: String,
    )

    companion object {
        private val SETS = listOf(
            SlideSet(
                "Marie",
                listOf("marie", "marie_mam", "marie_cin", "marie_oro", "marie_princess", "marie_white", "marie_mon", "prn_marie"),
                "marie",
            ),
            SlideSet("눈미끄럼틀", listOf("prn_slide1", "prn_slide2", "prn_slide3"), "prn_slide"),
            SlideSet("눈사람 만들래", listOf("prn_snowman1", "prn_snowman2", "prn_snowman3"), "prn_snowman"),
        )
    }
}
