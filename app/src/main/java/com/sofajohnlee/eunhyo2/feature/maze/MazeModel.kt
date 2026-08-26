package com.sofajohnlee.eunhyo2.feature.maze

enum class MazeDirection { UP, DOWN, RIGHT, LEFT }

data class MazeLevel(
    val number: Int,
    val verticalWalls: List<List<Boolean>>,
    val horizontalWalls: List<List<Boolean>>,
    val startX: Int,
    val startY: Int,
    val goalX: Int,
    val goalY: Int,
) {
    val width: Int = horizontalWalls.first().size
    val height: Int = verticalWalls.size
}

class MazeState(val level: MazeLevel) {
    var x: Int = level.startX
        private set
    var y: Int = level.startY
        private set
    val complete: Boolean get() = x == level.goalX && y == level.goalY

    fun move(direction: MazeDirection): Boolean {
        val canMove = when (direction) {
            MazeDirection.UP -> y > 0 && !level.horizontalWalls[y - 1][x]
            MazeDirection.DOWN -> y < level.height - 1 && !level.horizontalWalls[y][x]
            MazeDirection.RIGHT -> x < level.width - 1 && !level.verticalWalls[y][x]
            MazeDirection.LEFT -> x > 0 && !level.verticalWalls[y][x - 1]
        }
        if (!canMove) return false
        when (direction) {
            MazeDirection.UP -> y--
            MazeDirection.DOWN -> y++
            MazeDirection.RIGHT -> x++
            MazeDirection.LEFT -> x--
        }
        return true
    }
}

object MazeCatalog {
    fun level(number: Int): MazeLevel = when (number) {
        2 -> level2
        3 -> level3
        else -> level1
    }

    private fun rows(vararg rows: String): List<List<Boolean>> = rows.map { row ->
        row.split(',').map { it.trim() == "1" }
    }

    private val level1 = MazeLevel(
        1,
        rows("1,0,0,0,1,0,0", "1,0,0,1,0,1,1", "0,1,0,0,1,0,0", "0,1,1,0,0,0,1", "1,0,0,0,1,1,0", "0,1,0,0,1,0,0", "0,1,1,1,1,1,0", "0,0,0,1,0,0,0"),
        rows("0,0,1,1,0,0,1,0", "0,0,1,1,0,1,0,0", "1,1,0,1,1,0,1,1", "0,0,1,0,1,1,0,0", "0,1,1,1,1,0,1,1", "1,0,0,1,0,0,1,0", "0,1,0,0,0,1,0,1"),
        0, 0, 7, 7,
    )

    private val level2 = MazeLevel(
        2,
        rows("0,0,0,1,0,0,0", "0,0,1,0,1,0,0", "0,0,1,1,0,0,0", "0,0,1,1,1,0,0", "0,0,1,0,1,0,0", "1,0,0,1,0,1,0", "1,0,1,1,0,0,0", "0,0,1,0,0,0,1"),
        rows("0,1,1,1,0,1,1,1", "1,1,0,0,1,1,1,0", "0,1,1,0,0,0,1,1", "1,1,0,0,0,1,1,0", "0,1,1,1,1,0,1,0", "0,0,1,0,0,1,1,1", "0,1,0,0,1,1,0,0"),
        0, 7, 7, 0,
    )

    private val level3 = MazeLevel(
        3,
        rows(
            "0,0,1,0,0,0,1,0,0,0,0,0", "0,1,0,0,0,1,0,0,0,0,1,1", "1,0,0,0,0,1,0,0,0,0,1,1", "1,1,0,0,0,1,1,1,0,0,1,1", "1,1,1,0,0,1,1,0,1,0,1,1", "0,1,1,1,0,1,0,0,0,1,0,0", "0,0,0,1,0,1,0,1,0,0,0,0", "0,0,1,0,1,0,1,1,0,1,0,0", "1,1,1,1,0,1,1,0,0,1,0,0", "0,0,0,1,0,0,1,1,0,1,1,0", "0,0,1,0,1,0,1,0,0,0,0,0", "1,1,1,1,1,1,1,0,0,1,0,0", "0,0,1,0,0,1,0,0,0,0,1,0"
        ),
        rows(
            "1,0,0,1,1,0,0,0,1,1,1,1,0", "0,1,1,1,1,1,1,1,1,1,0,0,0", "0,0,1,1,1,0,0,1,1,1,1,0,0", "0,0,0,1,1,1,0,0,0,1,0,0,0", "0,0,0,0,1,0,0,1,1,1,0,0,0", "1,1,0,0,0,1,1,1,1,0,1,1,1", "0,1,1,1,1,1,0,0,0,1,1,1,0", "1,0,0,0,1,0,1,0,1,0,0,1,1", "0,1,0,0,0,1,0,1,1,1,1,1,0", "1,1,0,1,0,1,1,0,0,1,0,1,0", "0,1,1,0,1,0,0,1,1,0,1,1,1", "0,1,0,0,1,0,0,1,1,1,0,0,1"
        ),
        0, 0, 12, 12,
    )
}
