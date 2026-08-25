package com.sofajohnlee.eunhyo2.feature.game

import org.junit.Assert.assertEquals
import org.junit.Test

class BoardGameScoreCalculatorTest {
    @Test fun knightNormal() = assertEquals(7, BoardGameScoreCalculator.knight(3, 4, false))
    @Test fun knightDouble() = assertEquals(14, BoardGameScoreCalculator.knight(3, 4, true))
    @Test fun farmerScore() = assertEquals(15, BoardGameScoreCalculator.farmer(5))
    @Test fun negativeValuesAreClamped() = assertEquals(0, BoardGameScoreCalculator.farmer(-1))
}
