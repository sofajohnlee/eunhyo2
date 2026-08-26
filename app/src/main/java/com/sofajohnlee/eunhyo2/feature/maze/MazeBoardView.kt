package com.sofajohnlee.eunhyo2.feature.maze

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class MazeBoardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {
    var state: MazeState? = null
        set(value) {
            field = value
            invalidate()
        }

    private val wallPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }
    private val markerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val current = state ?: return
        val level = current.level
        val cell = minOf(width / level.width.toFloat(), height / level.height.toFloat())
        val boardWidth = cell * level.width
        val boardHeight = cell * level.height
        val left = (width - boardWidth) / 2f
        val top = (height - boardHeight) / 2f

        canvas.drawRect(RectF(left, top, left + boardWidth, top + boardHeight), wallPaint)
        level.verticalWalls.forEachIndexed { y, row ->
            row.forEachIndexed { x, wall ->
                if (wall) {
                    val px = left + (x + 1) * cell
                    val py = top + y * cell
                    canvas.drawLine(px, py, px, py + cell, wallPaint)
                }
            }
        }
        level.horizontalWalls.forEachIndexed { y, row ->
            row.forEachIndexed { x, wall ->
                if (wall) {
                    val px = left + x * cell
                    val py = top + (y + 1) * cell
                    canvas.drawLine(px, py, px + cell, py, wallPaint)
                }
            }
        }

        val goalCx = left + (level.goalX + 0.5f) * cell
        val goalCy = top + (level.goalY + 0.5f) * cell
        canvas.drawCircle(goalCx, goalCy, cell * 0.22f, markerPaint)

        val playerCx = left + (current.x + 0.5f) * cell
        val playerCy = top + (current.y + 0.5f) * cell
        canvas.drawCircle(playerCx, playerCy, cell * 0.34f, markerPaint)
    }
}
