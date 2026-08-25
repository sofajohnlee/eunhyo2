package com.sofajohnlee.eunhyo2.feature.game

object BoardGameScoreCalculator {
    fun knight(first: Int, second: Int, doubled: Boolean): Int {
        val multiplier = if (doubled) 2 else 1
        return (first.coerceAtLeast(0) + second.coerceAtLeast(0)) * multiplier
    }

    fun farmer(value: Int): Int = value.coerceAtLeast(0) * 3
}
