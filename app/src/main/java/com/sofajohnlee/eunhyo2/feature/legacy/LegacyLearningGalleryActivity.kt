package com.sofajohnlee.eunhyo2.feature.legacy

import android.graphics.Matrix
import android.graphics.PointF
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sofajohnlee.eunhyo2.databinding.ActivityLegacyLearningGalleryBinding
import kotlin.math.sqrt

/**
 * Displays legacy image-only learning materials after the original drawable
 * assets are imported with tools/import_legacy_binary_assets.sh.
 *
 * Resource lookup is dynamic so the modern project remains buildable before
 * the binary migration step is performed.
 */
class LegacyLearningGalleryActivity : AppCompatActivity(), View.OnTouchListener {
    private lateinit var binding: ActivityLegacyLearningGalleryBinding
    private var index = 0

    private val matrix = Matrix()
    private val savedMatrix = Matrix()
    private val start = PointF()
    private val mid = PointF()
    private var mode = NONE
    private var oldDist = 1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLegacyLearningGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageLegacy.scaleType = android.widget.ImageView.ScaleType.MATRIX
        binding.imageLegacy.setOnTouchListener(this)
        binding.buttonPrevious.setOnClickListener { move(-1) }
        binding.buttonNext.setOnClickListener { move(1) }
        showCurrent()
    }

    private fun move(delta: Int) {
        index = (index + delta + ITEMS.size) % ITEMS.size
        matrix.reset()
        showCurrent()
    }

    private fun showCurrent() {
        val item = ITEMS[index]
        binding.textLegacyTitle.text = item.title
        val id = resources.getIdentifier(item.resourceName, "drawable", packageName)
        if (id == 0) {
            binding.imageLegacy.setImageDrawable(null)
            binding.textLegacyStatus.text = "원본 자산 '${item.resourceName}'이 아직 없습니다. 원본 자산 가져오기 스크립트를 실행하면 표시됩니다."
        } else {
            binding.imageLegacy.setImageResource(id)
            binding.imageLegacy.imageMatrix = matrix
            binding.textLegacyStatus.text = "${index + 1} / ${ITEMS.size} · 드래그/핀치 확대 가능"
        }
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                savedMatrix.set(matrix)
                start.set(event.x, event.y)
                mode = DRAG
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                oldDist = spacing(event)
                if (oldDist > 10f) {
                    savedMatrix.set(matrix)
                    midPoint(mid, event)
                    mode = ZOOM
                }
            }
            MotionEvent.ACTION_MOVE -> when (mode) {
                DRAG -> {
                    matrix.set(savedMatrix)
                    matrix.postTranslate(event.x - start.x, event.y - start.y)
                }
                ZOOM -> {
                    val newDist = spacing(event)
                    if (newDist > 10f) {
                        matrix.set(savedMatrix)
                        val scale = newDist / oldDist
                        matrix.postScale(scale, scale, mid.x, mid.y)
                    }
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL -> mode = NONE
        }
        binding.imageLegacy.imageMatrix = matrix
        return true
    }

    private fun spacing(event: MotionEvent): Float {
        if (event.pointerCount < 2) return 0f
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return sqrt(x * x + y * y)
    }

    private fun midPoint(point: PointF, event: MotionEvent) {
        if (event.pointerCount < 2) return
        point.set((event.getX(0) + event.getX(1)) / 2f, (event.getY(0) + event.getY(1)) / 2f)
    }

    data class Item(val title: String, val resourceName: String)

    companion object {
        private const val NONE = 0
        private const val DRAG = 1
        private const val ZOOM = 2

        private val ITEMS = buildList {
            add(Item("수의 체계", "math_m12"))
            add(Item("영어 문장 따라쓰기 A0", "st_a0_001"))
            add(Item("영어 문장 따라쓰기 A1", "st_a1_001"))
            add(Item("영어 문장 따라쓰기 A2", "st_a2_001"))
            add(Item("영어 문장 따라쓰기 B1", "st_b1_001"))
            add(Item("영어 문장 따라쓰기 B2", "st_b2_001"))
            add(Item("영어 문장 따라쓰기 C1", "st_c1_001"))
            (1..27).forEach { number ->
                add(Item("영어 그림 학습 ${number}", "eengv${number}"))
            }
        }
    }
}
