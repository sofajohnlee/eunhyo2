package com.sofajohnlee.eunhyo2.feature.school

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivitySchoolMenuBinding
import com.sofajohnlee.eunhyo2.feature.ai.AiChatActivity
import com.sofajohnlee.eunhyo2.feature.clock.ClockStudyActivity
import com.sofajohnlee.eunhyo2.feature.drawing.DrawingPracticeActivity
import com.sofajohnlee.eunhyo2.feature.english.EnglishSentenceImportActivity
import com.sofajohnlee.eunhyo2.feature.english.EnglishStudyActivity
import com.sofajohnlee.eunhyo2.feature.game.BoardGameScoreActivity
import com.sofajohnlee.eunhyo2.feature.geometry.GeometryStudyActivity
import com.sofajohnlee.eunhyo2.feature.graph.GraphToolsActivity
import com.sofajohnlee.eunhyo2.feature.hanja.HanjaStudyActivity
import com.sofajohnlee.eunhyo2.feature.history.HistoryStudyActivity
import com.sofajohnlee.eunhyo2.feature.korean.KoreanPhraseActivity
import com.sofajohnlee.eunhyo2.feature.korean.KoreanStudyActivity
import com.sofajohnlee.eunhyo2.feature.links.EducationLinksActivity
import com.sofajohnlee.eunhyo2.feature.math.MathStudyActivity
import com.sofajohnlee.eunhyo2.feature.math.MeasurementActivity
import com.sofajohnlee.eunhyo2.feature.media.MediaLibraryActivity
import com.sofajohnlee.eunhyo2.feature.sports.SportsActivity

class SchoolMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySchoolMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchoolMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val level = intent.getStringExtra(EXTRA_LEVEL)
            ?.let { runCatching { SchoolLevel.valueOf(it) }.getOrNull() }
            ?: SchoolLevel.ELEMENTARY
        binding.textSchoolLevel.text = level.label

        binding.buttonKorean.setOnClickListener { open(KoreanStudyActivity::class.java) }
        binding.buttonKoreanPhrase.setOnClickListener { open(KoreanPhraseActivity::class.java) }
        binding.buttonEnglish.setOnClickListener { open(EnglishStudyActivity::class.java) }
        binding.buttonEnglishSentence.setOnClickListener { open(EnglishSentenceImportActivity::class.java) }
        binding.buttonMath.setOnClickListener { open(MathStudyActivity::class.java) }
        binding.buttonMeasurement.setOnClickListener { open(MeasurementActivity::class.java) }
        binding.buttonGeometry.setOnClickListener { open(GeometryStudyActivity::class.java) }
        binding.buttonGraph.setOnClickListener { open(GraphToolsActivity::class.java) }
        binding.buttonHanja.setOnClickListener { open(HanjaStudyActivity::class.java) }
        binding.buttonHistory.setOnClickListener { open(HistoryStudyActivity::class.java) }
        binding.buttonClock.setOnClickListener { open(ClockStudyActivity::class.java) }
        binding.buttonDrawing.setOnClickListener { open(DrawingPracticeActivity::class.java) }
        binding.buttonLinks.setOnClickListener { open(EducationLinksActivity::class.java) }
        binding.buttonMedia.setOnClickListener { open(MediaLibraryActivity::class.java) }
        binding.buttonSports.setOnClickListener { open(SportsActivity::class.java) }
        binding.buttonAi.setOnClickListener { open(AiChatActivity::class.java) }
        binding.buttonGame.setOnClickListener { open(BoardGameScoreActivity::class.java) }
    }

    private fun open(target: Class<out AppCompatActivity>) {
        startActivity(Intent(this, target))
    }

    companion object {
        const val EXTRA_LEVEL = "school_level"
    }
}

enum class SchoolLevel(val label: String) {
    ELEMENTARY("초등학교"),
    MIDDLE("중학교"),
    HIGH("고등학교"),
}
