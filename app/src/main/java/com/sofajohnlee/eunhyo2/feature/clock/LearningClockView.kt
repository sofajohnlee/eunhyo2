package com.sofajohnlee.eunhyo2.feature.clock

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class LearningClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var hour = 12
    private var minute = 0

    fun setTime(hour: Int, minute: Int) {
        this.hour = hour
        this.minute = minute
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val cx = width / 2f
        val cy = height / 2f
        val radius = min(width, height) * 0.42f

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
        canvas.drawCircle(cx, cy, radius, paint)

        for (i in 0 until 12) {
            val angle = i * 2 * PI / 12 - PI / 2
            val x1 = cx + cos(angle).toFloat() * radius * 0.88f
            val y1 = cy + sin(angle).toFloat() * radius * 0.88f
            val x2 = cx + cos(angle).toFloat() * radius
            val y2 = cy + sin(angle).toFloat() * radius
            canvas.drawLine(x1, y1, x2, y2, paint)
        }

        val minuteAngle = minute * 2 * PI / 60 - PI / 2
        val hourAngle = ((hour % 12) + minute / 60.0) * 2 * PI / 12 - PI / 2

        paint.strokeWidth = 7f
        canvas.drawLine(
            cx,
            cy,
            cx + cos(hourAngle).toFloat() * radius * 0.55f,
            cy + sin(hourAngle).toFloat() * radius * 0.55f,
            paint,
        )
        paint.strokeWidth = 4f
        canvas.drawLine(
            cx,
            cy,
            cx + cos(minuteAngle).toFloat() * radius * 0.8f,
            cy + sin(minuteAngle).toFloat() * radius * 0.8f,
            paint,
        )
        paint.style = Paint.Style.FILL
        canvas.drawCircle(cx, cy, 8f, paint)
    }
}
