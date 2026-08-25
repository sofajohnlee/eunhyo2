package com.sofajohnlee.eunhyo2.feature.geometry

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class GeometryCanvasView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {
    private val stroke = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 8f
    }
    private val guide = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }

    var category: GeometryCategory = GeometryCategory.CIRCLE
        set(value) {
            field = value
            invalidate()
        }

    var title: String = "원"
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val cx = width / 2f
        val cy = height / 2f
        val size = min(width, height) * 0.32f
        when (category) {
            GeometryCategory.CIRCLE -> drawCircle(canvas, cx, cy, size)
            GeometryCategory.TRIANGLE -> drawTriangle(canvas, cx, cy, size)
            GeometryCategory.RECTANGLE -> drawRectangle(canvas, cx, cy, size)
            GeometryCategory.SOLID -> drawSolid(canvas, cx, cy, size)
        }
    }

    private fun drawCircle(canvas: Canvas, cx: Float, cy: Float, size: Float) {
        canvas.drawCircle(cx, cy, size, stroke)
        if (title == "반지름") {
            canvas.drawLine(cx, cy, cx + size, cy, guide)
        } else if (title == "지름") {
            canvas.drawLine(cx - size, cy, cx + size, cy, guide)
        }
    }

    private fun drawTriangle(canvas: Canvas, cx: Float, cy: Float, size: Float) {
        val path = Path().apply {
            moveTo(cx, cy - size)
            lineTo(cx - size, cy + size)
            lineTo(cx + size, cy + size)
            close()
        }
        canvas.drawPath(path, stroke)
        if (title == "직각삼각형") {
            val right = Path().apply {
                moveTo(cx - size * 0.7f, cy + size)
                lineTo(cx - size * 0.7f, cy + size * 0.7f)
                lineTo(cx - size * 0.4f, cy + size * 0.7f)
            }
            canvas.drawPath(right, guide)
        }
    }

    private fun drawRectangle(canvas: Canvas, cx: Float, cy: Float, size: Float) {
        val halfW = if (title == "정사각형") size else size * 1.25f
        canvas.drawRect(cx - halfW, cy - size * 0.75f, cx + halfW, cy + size * 0.75f, stroke)
        if (title == "평행사변형") {
            val path = Path().apply {
                moveTo(cx - size, cy + size * 0.7f)
                lineTo(cx - size * 0.55f, cy - size * 0.7f)
                lineTo(cx + size, cy - size * 0.7f)
                lineTo(cx + size * 0.55f, cy + size * 0.7f)
                close()
            }
            canvas.drawPath(path, stroke)
        }
    }

    private fun drawSolid(canvas: Canvas, cx: Float, cy: Float, size: Float) {
        if (title == "원기둥") {
            canvas.drawOval(cx - size, cy - size, cx + size, cy - size * 0.45f, stroke)
            canvas.drawOval(cx - size, cy + size * 0.45f, cx + size, cy + size, stroke)
            canvas.drawLine(cx - size, cy - size * 0.72f, cx - size, cy + size * 0.72f, stroke)
            canvas.drawLine(cx + size, cy - size * 0.72f, cx + size, cy + size * 0.72f, stroke)
            return
        }
        val depth = size * 0.35f
        val left = cx - size
        val top = cy - size * 0.7f
        val right = cx + size
        val bottom = cy + size * 0.7f
        canvas.drawRect(left, top, right, bottom, stroke)
        canvas.drawRect(left + depth, top - depth, right + depth, bottom - depth, guide)
        canvas.drawLine(left, top, left + depth, top - depth, guide)
        canvas.drawLine(right, top, right + depth, top - depth, guide)
        canvas.drawLine(right, bottom, right + depth, bottom - depth, guide)
        canvas.drawLine(left, bottom, left + depth, bottom - depth, guide)
    }
}
